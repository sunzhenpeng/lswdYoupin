package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.UserThin;
import com.lswd.youpin.common.util.Verifycode;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.UserService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.DataSourceHandle;
import com.lswd.youpin.utils.MD5Utils;
import com.lswd.youpin.web.utils.GetImag;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


/**
 * Created by liruilong on 2017/4/12.
 */
@Api(value = "user", tags = "user", description = "用户管理")
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserThin userThin;

    @Value("${cookie.uuid.expiry}")
    private Integer cookieUuidExpiry;//cookie时长

    @Value("${cookie.uuid.name}")//cookie名称
    private String cookieUuidName;

    @ApiOperation(value = "查询账户信息", notes = "查询账户信息", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getUserName/{username}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getUser(@PathVariable("username") String username) {
        LsResponse lsResponse=  userThin.getUserByName(username);
        return lsResponse;
    }

    @ApiOperation(value = "用户登录操作", notes = "用户登录操作", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse login(@RequestBody String data, HttpServletRequest request) {
        DataSourceHandle.setDataSourceType("LSWD");
        return userService.login(data, request);
    }


    @ApiOperation(value = "用户注册", notes = "用户注册", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse signUp(@RequestBody User user, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        return userService.signUp(user, u);
    }

    @ApiOperation(value = "初始化用户密码", notes = "初始化用户密码", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/resetPassword/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse resetPwd(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return userService.restPassword(id, user);
    }

    @ApiOperation(value = "修改登录密码", notes = "修改登录密码", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse password(@RequestBody String data, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = userService.updatePwd(data, user);
        return lsResponse;
    }

    @ApiOperation(value = "添加用户", notes = "添加用户", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse add(@RequestBody User user, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        return userService.add(user, u);
    }

    @ApiOperation(value = "删除用户", notes = "删除用户", nickname = "liuhao", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteUser(@PathVariable("id") Integer id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return userService.deleteUser(id, user);
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateUser(@RequestBody User user, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        return userService.updateUser(user, u);
    }

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getUsers(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                               @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                               @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                               HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return userThin.getUsers(user, pageNum, pageSize, keyword);
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/out", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse loginOut(HttpServletRequest request) {
        String token = request.getHeader("token");
        return userService.loginOut(token);
    }

    /**
     * @author liuhao
     * 功能 生成图片验证码
     */
    @ApiOperation(value = "图片验证码", notes = "图片验证码", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAuthCode(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        char[] ch = "0123456789".toCharArray();//显示的字符
        int len = ch.length;//字符数组长度
        int index = 0;//要显示字符的下表
        int width = 75;//图片宽度
        int height = 35;//图片高度
        //设置response头信息
        //禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height, 1);
        //产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        //Graphics类的样式
        g.setColor(Verifycode.getRandColor(200, 250));
        g.setFont(new Font("Times New Roman", 0, 28));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        Random random = new Random();//产生随机数
        for (int i = 0; i < 40; i++) {
            g.setColor(Verifycode.getRandColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }
        //绘制字符
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            index = random.nextInt(len);
            sb.append(ch[index]);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(ch[index] + "", 15 * i + 4, 28);
        }
        HttpSession session = request.getSession();
        session.setAttribute("verifyCode", sb.toString());
        session.getId();
        g.dispose();
        LsResponse lsResponse = new LsResponse();
        String imageCode = GetImag.encodeImgageToBase64(image);
        String codeUUID = MD5Utils.getMD5ForRandomUUID();
        System.out.println("eString=====================info");
        try {
            Jedis jedis = RedisManager.getJedis();
            jedis.set(codeUUID, sb.toString());
            jedis.expire(codeUUID, 120);
            RedisManager.returnResource(jedis);
            lsResponse.setData(imageCode);
            lsResponse.setMessage(codeUUID);
            //response.getOutputStream().flush();
        } catch (Exception e) {
            System.out.println("eString====================="+e);
        }

        return lsResponse;
    }
}
