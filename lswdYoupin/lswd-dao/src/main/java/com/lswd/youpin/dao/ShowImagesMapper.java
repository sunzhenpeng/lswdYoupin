package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.ShowImagesMapperGen;
import com.lswd.youpin.model.ShowImages;
import com.lswd.youpin.model.vo.CanteenImageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShowImagesMapper extends ShowImagesMapperGen {
    List<ShowImages> getImagesWeb(@Param("canteenId") String canteenId);
    List<CanteenImageVO> getImagesH5(@Param("canteenId") String canteenId);

}