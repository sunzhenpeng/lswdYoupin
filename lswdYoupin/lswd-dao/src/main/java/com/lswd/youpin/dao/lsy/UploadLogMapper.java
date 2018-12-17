package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.UploadLogMapperGen;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UploadLogMapper extends UploadLogMapperGen {


    List<Map<String,Object>> getUploadLogApp(@Param(value = "userName") String userName);


}