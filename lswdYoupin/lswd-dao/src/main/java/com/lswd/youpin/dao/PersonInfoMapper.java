package com.lswd.youpin.dao;

import com.lswd.youpin.model.PersonInfo;
import com.lswd.youpin.model.vo.PersonInfoCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by H61M-K on 2018/2/7.
 */
public interface PersonInfoMapper {
    List<PersonInfo> getAll();

    List<PersonInfo> getPersonInfoTime(@Param(value = "startTime")String startTime,@Param(value = "endTime")String entTime);

    List<PersonInfoCount> getStuCountClass();

    List<PersonInfo> getRegionStudentCount();

    Integer insertPersonInfoStudent(PersonInfo personInfo);

    Integer insertPersonInfoTeacher(PersonInfo personInfo);
}
