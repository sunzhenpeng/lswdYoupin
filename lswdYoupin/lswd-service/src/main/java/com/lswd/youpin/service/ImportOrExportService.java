package com.lswd.youpin.service;

import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/7/3.
 */
public interface ImportOrExportService {
    //LsResponse exportExecl(Integer flag, Boolean b, HttpServletResponse response);

    LsResponse exportExeclModel(Integer flag,HttpServletResponse response);

    LsResponse exportExeclList(String[] canteenIds, List<Canteen> canteens,Integer flag, HttpServletResponse response);

    LsResponse improtExecl(User user,Integer flag,String canteenId,MultipartFile file);

    LsResponse importExcelYXStudent(User user);

    LsResponse importExcelYXTeacher(User user);
}
