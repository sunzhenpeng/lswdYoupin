package com.lswd.youpin.service;

import com.lswd.youpin.model.PersonInfo;
import com.lswd.youpin.response.LsResponse;

import java.util.List;

/**
 * Created by H61M-K on 2018/2/7.
 */
public interface PersonInfoService {
    List<PersonInfo> getAll();

    LsResponse getStuCountClass();

    LsResponse getRegionStudentCount();

    void synPersonInfo();
}
