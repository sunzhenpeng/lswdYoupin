package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.ResourceMapperGen;
import com.lswd.youpin.model.lsyp.Resources;
import com.lswd.youpin.model.vo.Nodes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceMapper extends ResourceMapperGen {

    List<Resources> getResourceByRoleId(@Param("id") Integer id);

    List<Resources> getResourceListAll();

    List<Nodes> getResource(@Param("roleId") Integer roleId);

}