package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.DiskLabelMapperGen;
import com.lswd.youpin.model.lsyp.DiskLabel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiskLabelMapper extends DiskLabelMapperGen {

    List<DiskLabel> getDiskLabelByDiskTypeId(@Param(value = "diskTypeId")Integer diskTypeId);
}