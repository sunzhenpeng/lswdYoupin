package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.MemberTypeMapperGen;
import com.lswd.youpin.model.lsyp.MemberType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberTypeMapper extends MemberTypeMapperGen {

    List<MemberType> getMemberTypeList(@Param(value = "name")String name);

    List<MemberType> getMemberTypeListAll();

}