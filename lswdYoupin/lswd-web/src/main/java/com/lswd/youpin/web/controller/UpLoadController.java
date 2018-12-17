package com.lswd.youpin.web.controller;

import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.dao.CanteenMapper;
import com.lswd.youpin.dao.lsy.ResTypeMapper;
import com.lswd.youpin.lsy.ImgService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Img;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.response.LsyResponse;
import com.lswd.youpin.utils.RandomUtil;
import io.swagger.annotations.Api;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liruilong on 2017/6/7.
 */
@Api(value = "upload", tags = "upload", description = "文件上传111123")
@Controller
@RequestMapping(value = "/upload")

public class UpLoadController {
    private final Logger log = LoggerFactory.getLogger(UpLoadController.class);
    @Autowired
    CanteenMapper canteenMapper;
    @Autowired
    ResTypeMapper resTypeMapper;
    @Autowired
    ImgService imgService;
    /**
     * 异步上传处理
     *
     * @param request
     * @param
     * @param file
     * @return 返回上传文件相对路径及名称
     * @throws
     */
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse uploadFileHandler(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String canteenId = request.getHeader(ConstantsCode.CANTEEN_ID);
        log.info("canteenId================================================:{}",canteenId);
        String code;
        if (canteenId == null) {
            User u = (User) request.getAttribute("user");
            code = u.getTenantId().substring(0, 4).toLowerCase();
        } else {
            code = canteenId.substring(0, 4).toLowerCase();
        }
        LsResponse lsResponse = new LsResponse();
        StringBuffer path = new StringBuffer("../webapps/upload/images/");
        path.append(code).append("/");
        String fileName = file.getOriginalFilename();
        String fName = "";
        String suffix;
        String realPath = "";
         StringBuffer stringBuffer = new StringBuffer("https://web.lsypct.com/upload/images/");
          //StringBuffer stringBuffer = new StringBuffer("http://127.0.0.1:8080/upload/images/");
        if (fileName.lastIndexOf(".") >= 0) {
            int indexDot = fileName.lastIndexOf(".");
            suffix = fileName.substring(indexDot);
            String prefix = RandomUtil.getRandomCodeStr(2);
            fName = prefix + suffix;
            realPath = stringBuffer.append(code).append("/").append(fName).toString();
            log.info("fName=" + fName);
        }
        File targetFile = new File(String.valueOf(path), "");
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            // 先尝试压缩并保存图片
            Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(path + fName);
        } catch (IOException e) {
            log.info("Thumbnails压缩出错============：{}", e.getMessage());
            try {
                // 失败了再用springmvc自带的方式
                file.transferTo(targetFile);
            } catch (IOException e1) {
                log.info("springmvc自带上传图片出错===========：{}", e1.getMessage());
            }
        }
        lsResponse.setData(realPath);
        return lsResponse;
    }


/*
    @RequestMapping(value = "/uploadWithHeader", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse uploadWithHeaderHandler(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String resTypeId = request.getHeader("resTypeId");
        String resType= request.getHeader("resType");
        String canteenId = "";
        try {
            canteenId = canteenMapper.getCanteenIdByResTypeId(Integer.parseInt(resTypeId));
        } catch (Exception e) {
            log.info(e.toString());
        }
        LsResponse lsResponse = new LsResponse();
        log.info("resTypeId===============:{}",resTypeId);
        log.info("canteenId===============:{}",canteenId);

        String codeOne  = "";
        String codeTwo = "" ;
        String codeThree = "" ;
        if (canteenId == null) {
           *//* User u = (User) request.getAttribute("user");
            codeOne = u.getTenantId().substring(0, 4).toLowerCase();*//*
           codeOne  = ConstantsCode.LSCT;
        } else {
            codeOne = canteenId.substring(0, 4).toLowerCase();
        }

        if (resType == null) {
            codeTwo = ConstantsCode.GENERAL;
        } else {
            codeTwo = resType;
        }

        if (resTypeId == null) {
            codeThree = ConstantsCode.GENERAL;
        } else {
            codeThree = resTypeId;
        }
        StringBuffer path = new StringBuffer("../webapps/images/");
        path.append(codeOne).append("/").append(codeTwo).append("/").append(codeThree).append("/");
        String fileName = file.getOriginalFilename();
        String fName = "";
        String suffix;
        String realPath = "";
        // StringBuffer stringBuffer = new StringBuffer("YUN_UPDATE_URL");
        StringBuffer stringBuffer = new StringBuffer(ConstantsCode.YUN_UPDATE_URL);
        if (fileName.lastIndexOf(".") >= 0) {
            int indexDot = fileName.lastIndexOf(".");
            suffix = fileName.substring(indexDot);
            String prefix = RandomUtil.getRandomCodeStr(2);
            fName = prefix + suffix;
            realPath = stringBuffer.append(codeOne).append("/").append(codeTwo).append("/").append(codeThree).append("/").append(fName).toString();
            log.info("fName=" + fName);
        }
        File targetFile = new File(String.valueOf(path), "");
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            // 先尝试压缩并保存图片
            Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(path + fName);

        } catch (IOException e) {
            log.info("Thumbnails压缩出错==============：{}", e.getMessage());
            try {
                // 失败了再用springmvc自带的方式
                file.transferTo(targetFile);
            } catch (IOException e1) {
                log.info("springmvc自带上传图片出错==========：{}", e1.getMessage());
            }
        }
        lsResponse.setData(realPath);
        return lsResponse;
    }*/


    @RequestMapping(value = "/uploadRes", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse uploadResHandler(HttpServletRequest request,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam(value = "resTypeId", required = true) Integer resTypeId
    ) {
      /*  String resTypeId = request.getHeader("resTypeId");
        String resType= request.getHeader("resType");*/
        String pageId = "" ;
        String canteenId = "";
        try {
            canteenId = canteenMapper.getCanteenIdByResTypeId(resTypeId);
            pageId = resTypeMapper.getPageIdByResTypeId(resTypeId);
        } catch (Exception e) {
            log.info(e.toString());
        }

        log.info("resTypeId===============:{}",resTypeId);
        log.info("canteenId===============:{}",canteenId);

        String codeOne  = "";
        String codeTwo = "" ;
        if (canteenId != null&&!"".equals(canteenId)) {
            codeOne = canteenId.substring(0, 4).toLowerCase();
        } else {
            codeOne  = ConstantsCode.LSCT;
        }

        if (pageId != null&&!"".equals(pageId)) {
            codeTwo = pageId;
        } else {
            codeTwo = ConstantsCode.GENERAL;
        }
        LsResponse lsResponse = new LsResponse();
        StringBuffer path = new StringBuffer("../webapps/upload/images/");
        path.append(codeOne).append("/").append(codeTwo).append("/");
        StringBuffer smallPath = new StringBuffer("../webapps/upload/images/");
        smallPath.append(codeOne).append("/").append(codeTwo).append("/").append("small").append("/");
        String fileName = file.getOriginalFilename();
        String fName = "";
        String smallName = "";
        String suffix;
        String realPath = "";
        String smallRealPath = "";
        // StringBuffer stringBuffer = new StringBuffer("YUN_UPDATE_URL");
        StringBuffer stringBuffer = new StringBuffer(ConstantsCode.YUN_UPDATE_URL+"images/");
        StringBuffer stringBuffer02 = new StringBuffer(ConstantsCode.YUN_UPDATE_URL+"images/");
        if (fileName.lastIndexOf(".") >= 0) {
            int indexDot = fileName.lastIndexOf(".");
            suffix = fileName.substring(indexDot);
            String prefix = RandomUtil.getRandomCodeStr(2);
            fName = prefix + suffix;
            smallName = "small"+prefix + suffix;
            realPath = stringBuffer.append(codeOne).append("/").append(codeTwo).append("/").append(fName).toString();
            smallRealPath =  stringBuffer02.append(codeOne).append("/").append(codeTwo).append("/").append("small").append("/").append(smallName).toString();
            log.info("fName==" + fName);
            log.info("fName==" + smallRealPath);
    }
        File targetFile = new File(String.valueOf(path), "");
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        File targetFile02 = new File(String.valueOf(smallPath), "");
        if (!targetFile02.exists()) {
            targetFile02.mkdirs();
        }
        try {
            // 先尝试压缩并保存图片
        //    Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(path + fName);

            OutputStream os=new FileOutputStream(path + fName);
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while((temp=is.read())!=(-1))
            {
                os.write(temp);
            }
            os.flush();
            os.close();
            is.close();
            Thumbnails.of(file.getInputStream()).size(200, 300).toFile(smallPath + smallName);
            log.info("realpath===========" + realPath);
        } catch (IOException e) {
            log.info("Thumbnails压缩出错===============：{}", e.getMessage());
            try {
                // 失败了再用springmvc自带的方式
                file.transferTo(targetFile);
            } catch (IOException e1) {
                log.info("springmvc自带上传图片出错===========：{}", e1.getMessage());
            }
        }
        log.info("realpath===========" + realPath);
        log.info("smallrealpath===========" + smallRealPath);
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("realPath",realPath);
        map.put("smallRealPath",smallRealPath);
        lsResponse.setData(map);
        return lsResponse;
    }

    @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
    @ResponseBody
    public LsyResponse doUpload(HttpServletRequest request,
                                @RequestParam("files") MultipartFile[] files,
                                @RequestParam(value = "resTypeId", required = true) Integer resTypeId
    ) {
        log.info("resTypeId=============：{}", resTypeId.toString());
        List<Map<String, Object>> imgList = new ArrayList< Map<String, Object>>();
      /*  String resTypeId = request.getHeader("resTypeId");
        String resType= request.getHeader("resType");*/
        String pageId = "" ;
        String canteenId = "";
        try {
            canteenId = canteenMapper.getCanteenIdByResTypeId(resTypeId);
        } catch (Exception e) {
            pageId = resTypeMapper.getPageIdByResTypeId(resTypeId);
        }

        String codeOne  = "";
        String codeTwo = "" ;
        if (canteenId != null&&!"".equals(canteenId)) {
            codeOne = canteenId.substring(0, 4).toLowerCase();
        } else {
            codeOne  = ConstantsCode.LSCT;
        }

        if (pageId != null&&!"".equals(pageId)) {
            codeTwo = pageId;
        } else {
            codeTwo = ConstantsCode.GENERAL;
        }
        LsyResponse lsyResponse = new LsyResponse();
        StringBuffer path = new StringBuffer("../webapps/upload/images/");
        path.append(codeOne).append("/").append(codeTwo).append("/");
        StringBuffer stringBuffer = new StringBuffer(ConstantsCode.YUN_UPDATE_URL+"images/");
        File targetFile = new File(String.valueOf(path), "");
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        log.info("path===============：{}", path.toString());
        try {

            for(int i=0;i<files.length;i++){

                String fileName = files[i].getOriginalFilename();
                String fName = "";
                String suffix;
                String realPath = "";
                Img img = new Img();
                // StringBuffer stringBuffer = new StringBuffer("YUN_UPDATE_URL");
                if (fileName.lastIndexOf(".") >= 0) {
                    int indexDot = fileName.lastIndexOf(".");
                    suffix = fileName.substring(indexDot);
                    String prefix = RandomUtil.getRandomCodeStr(2);
                    fName = prefix + suffix;
                    realPath = stringBuffer.append(codeOne).append("/").append(codeTwo).append("/").append(fName).toString();
                    log.info("fName==" + fName);


                }

                // 先尝试压缩并保存图片
                //    Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(path + fName);
                OutputStream os=new FileOutputStream(path + fName);
                //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
                InputStream is=files[i].getInputStream();
                int temp;
                //一个一个字节的读取并写入
                while((temp=is.read())!=(-1))
                {
                    os.write(temp);
                }
                os.flush();
                os.close();
                is.close();

                img.setImgUrl(realPath);
                img.setResTypeId(resTypeId);
                imgService.addOrUpdateImg(img);
                log.info("fileName===============：{}", fileName.toString());
            }

        } catch (IOException e) {
            log.info("Thumbnails压缩出错===============：{}", e.getMessage());
            try {
                // 失败了再用springmvc自带的方式
                for(int i=0;i<files.length;i++){
                    files[i].transferTo(targetFile);
                }

            } catch (IOException e1) {
                log.info("springmvc自带上传图片出错===========：{}", e1.getMessage());
            }
        }

        Map<String, Object> map=new HashMap<String, Object>();
        map.put("list","122");
        lsyResponse.setData(map);
        return lsyResponse;
    }


    @RequestMapping(value = "/uploadPdf", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse uploadPdf(HttpServletRequest request,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam(value = "resTypeId", required = true) Integer resTypeId
    ) {
      /*  String resTypeId = request.getHeader("resTypeId");
        String resType= request.getHeader("resType");*/
        String pageId = "" ;
        String canteenId = "";
        try {
            canteenId = canteenMapper.getCanteenIdByResTypeId(resTypeId);
            pageId = resTypeMapper.getPageIdByResTypeId(resTypeId);
        } catch (Exception e) {
            log.info(e.toString());
        }
        log.info("resTypeId===============:{}",resTypeId);
        log.info("canteenId===============:{}",canteenId);

        String codeOne  = "";
        String codeTwo = "" ;
        if (canteenId != null&&!"".equals(canteenId)) {
            codeOne = canteenId.substring(0, 4).toLowerCase();
        } else {
            codeOne  = ConstantsCode.LSCT;
        }

        if (pageId != null&&!"".equals(pageId)) {
            codeTwo = pageId;
        } else {
            codeTwo = ConstantsCode.GENERAL;
        }
        LsResponse lsResponse = new LsResponse();
        StringBuffer path = new StringBuffer("../webapps/upload/pdf/");
        path.append(codeOne).append("/").append(codeTwo).append("/");

        String fileName = file.getOriginalFilename();
        String fName = "";

        String suffix;
        String realPath = "";

        // StringBuffer stringBuffer = new StringBuffer("YUN_UPDATE_URL");
        StringBuffer stringBuffer = new StringBuffer(ConstantsCode.YUN_UPDATE_URL+"pdf/");
        StringBuffer stringBuffer02 = new StringBuffer(ConstantsCode.YUN_UPDATE_URL+"pdf/");
        if (fileName.lastIndexOf(".") >= 0) {
            int indexDot = fileName.lastIndexOf(".");
            suffix = fileName.substring(indexDot);
            String prefix = RandomUtil.getRandomCodeStr(2);
            fName = prefix + suffix;

            realPath = stringBuffer.append(codeOne).append("/").append(codeTwo).append("/").append(fName).toString();

            log.info("fName==" + fName);

        }
        File targetFile = new File(String.valueOf(path), "");
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        try {
            // 先尝试压缩并保存图片
            //    Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(path + fName);
            OutputStream os=new FileOutputStream(path + fName);
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while((temp=is.read())!=(-1))
            {
                os.write(temp);
            }
            os.flush();
            os.close();
            is.close();
            log.info("realpath===========" + realPath);
        } catch (IOException e) {
            log.info("Thumbnails压缩出错===============：{}", e.getMessage());
            try {
                // 失败了再用springmvc自带的方式
                file.transferTo(targetFile);
            } catch (IOException e1) {
                log.info("springmvc自带上传图片出错===========：{}", e1.getMessage());
            }
        }
        log.info("realpath===========" + realPath);
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("realPath",realPath);
        lsResponse.setData(map);
        return lsResponse;
    }

    @RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse uploadVideo(HttpServletRequest request,
                                @RequestParam("file") MultipartFile file,
                                @RequestParam(value = "pageId", required = true) String pageId
    ) {
        String canteenId = "";
        String codeOne  = "";
        String codeTwo = "" ;
        if (pageId != null && !(pageId.equals(""))) {
            try {
                canteenId = canteenMapper.getCanteenIdByPageId(Integer.parseInt(pageId));
                if (canteenId == null) {
                    codeOne  = ConstantsCode.LSCT;
                } else {
                    codeOne = canteenId.substring(0, 4).toLowerCase();
                }
            } catch (Exception e) {
                log.info(e.toString());
            }
            codeTwo = pageId;
        }else{
            codeTwo = ConstantsCode.GENERAL;
        }
        log.info("resTypeId===============:{}",pageId);
        log.info("canteenId===============:{}",canteenId);

        LsResponse lsResponse = new LsResponse();
        StringBuffer path = new StringBuffer("../webapps/upload/video/");
        path.append(codeOne).append("/").append(codeTwo).append("/");

        String fileName = file.getOriginalFilename();
        String fName = "";

        String suffix;
        String realPath = "";

        // StringBuffer stringBuffer = new StringBuffer("YUN_UPDATE_URL");
        StringBuffer stringBuffer = new StringBuffer(ConstantsCode.YUN_UPDATE_URL+"video/");
        if (fileName.lastIndexOf(".") >= 0) {
            int indexDot = fileName.lastIndexOf(".");
            suffix = fileName.substring(indexDot);
            String prefix = RandomUtil.getRandomCodeStr(2);
            fName = prefix + suffix;
            realPath = stringBuffer.append(codeOne).append("/").append(codeTwo).append("/").append(fName).toString();
            log.info("fName==" + fName);

        }
        File targetFile = new File(String.valueOf(path), "");
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        try {
            // 先尝试压缩并保存图片
            //    Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(path + fName);
            OutputStream os=new FileOutputStream(path + fName);
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while((temp=is.read())!=(-1))
            {
                os.write(temp);
            }
            os.flush();
            os.close();
            is.close();
            log.info("realpath===========" + realPath);
        } catch (IOException e) {
            log.info("Thumbnails压缩出错===============：{}", e.getMessage());
            try {
                // 失败了再用springmvc自带的方式
                file.transferTo(targetFile);
            } catch (IOException e1) {
                log.info("springmvc自带上传图片出错===========：{}", e1.getMessage());
            }
        }
        log.info("realpath===========" + realPath);
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("realPath",realPath);
        lsResponse.setData(map);
        return lsResponse;
    }

    @RequestMapping(value = "/uploadPageResAndBanner", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse uploadPageResAndBanner(HttpServletRequest request,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam(value = "pageId", required = false) String pageId
    ) {

        String canteenId = "";
        String codeOne  = "";
        String codeTwo = "" ;
        if (pageId != null && !(pageId.equals(""))) {
            try {
                canteenId = canteenMapper.getCanteenIdByPageId(Integer.parseInt(pageId));
                if (canteenId == null) {
                    codeOne  = ConstantsCode.LSCT;
                } else {
                    codeOne = canteenId.substring(0, 4).toLowerCase();
                }
            } catch (Exception e) {
                log.info(e.toString());
            }
            codeTwo = pageId;
        }else{
            codeTwo = ConstantsCode.GENERAL;
        }
        LsResponse lsResponse = new LsResponse();
        StringBuffer path = new StringBuffer("../webapps/upload/pageImages/");
        path.append(codeOne).append("/").append(codeTwo).append("/");
        StringBuffer smallPath = new StringBuffer("../webapps/upload/pageImages/");
        smallPath.append(codeOne).append("/").append(codeTwo).append("/").append("small").append("/");
        String fileName = file.getOriginalFilename();
        String fName = "";
        String smallName = "";
        String suffix;
        String realPath = "";
        String smallRealPath = "";
        // StringBuffer stringBuffer = new StringBuffer("YUN_UPDATE_URL");
        StringBuffer stringBuffer = new StringBuffer(ConstantsCode.YUN_UPDATE_URL+"pageImages/");
        StringBuffer stringBuffer02 = new StringBuffer(ConstantsCode.YUN_UPDATE_URL+"pageImages/");
        if (fileName.lastIndexOf(".") >= 0) {
            int indexDot = fileName.lastIndexOf(".");
            suffix = fileName.substring(indexDot);
            String prefix = RandomUtil.getRandomCodeStr(2);
            fName = prefix + suffix;
            smallName = "small"+prefix + suffix;
            realPath = stringBuffer.append(codeOne).append("/").append(codeTwo).append("/").append(fName).toString();
            smallRealPath =  stringBuffer02.append(codeOne).append("/").append(codeTwo).append("/").append("small").append("/").append(smallName).toString();
            log.info("fName==" + fName);
            log.info("fName==" + smallRealPath);
        }
        File targetFile = new File(String.valueOf(path), "");
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        File targetFile02 = new File(String.valueOf(smallPath), "");
        if (!targetFile02.exists()) {
            targetFile02.mkdirs();
        }
        try {
            // 先尝试压缩并保存图片
            //    Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(path + fName);

            OutputStream os=new FileOutputStream(path + fName);
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while((temp=is.read())!=(-1))
            {
                os.write(temp);
            }
            os.flush();
            os.close();
            is.close();
            Thumbnails.of(file.getInputStream()).size(200, 300).toFile(smallPath + smallName);
            log.info("realpath===========" + realPath);
        } catch (IOException e) {
            log.info("Thumbnails压缩出错===============：{}", e.getMessage());
            try {
                // 失败了再用springmvc自带的方式
                file.transferTo(targetFile);
            } catch (IOException e1) {
                log.info("springmvc自带上传图片出错===========：{}", e1.getMessage());
            }
        }
        log.info("realpath===========" + realPath);
        log.info("smallrealpath===========" + smallRealPath);
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("realPath",realPath);
        map.put("smallRealPath",smallRealPath);
        lsResponse.setData(map);
        return lsResponse;
    }



    /*@RequestMapping(value = "/photoUpload", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse uploadPhotoHandler(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        LsResponse lsResponse = new LsResponse();
      //  StringBuffer path = new StringBuffer("../webapps/images/");
        //StringBuffer path = new StringBuffer("D:\\apache-tomcat-7.0.82\\webapps\\");
        StringBuffer path = new StringBuffer("../webapps/images/");
        String typeFile = "12345";
        path.append(typeFile).append("/");
        String fileName = file.getOriginalFilename();
        String fName = "";
        String suffix;
        String realPath = "";
        StringBuffer stringBuffer = new StringBuffer("http://192.168.1.108:8080/images/");
        if (fileName.lastIndexOf(".") >= 0) {
            int indexDot = fileName.lastIndexOf(".");
            suffix = fileName.substring(indexDot);
            String prefix = RandomUtil.getRandomCodeStr(2);
            fName = prefix + suffix;
            realPath = stringBuffer.append(typeFile).append("/").append(fName).toString();
            log.info("fName=" + fName);
        }
        File targetFile = new File(String.valueOf(path), "");
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            // 先尝试压缩并保存图片
            Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(path + fName);
            //Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile("D:\\apache-tomcat-7.0.82\\webapps\\images" + fName);
        } catch (IOException e) {
            log.info("Thumbnails压缩出错============：{}", e.getMessage());
                try {
                    // 失败了再用springmvc自带的方式
                    file.transferTo(targetFile);

            } catch (IOException e1) {
                log.info("springmvc自带上传图片出错==========：{}", e1.getMessage());
            }
        }
        lsResponse.setData(realPath);
        return lsResponse;
    }*/

}
