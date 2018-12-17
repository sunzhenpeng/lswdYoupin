package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.GoodDetailsImgMapperGen;
import com.lswd.youpin.model.lsyp.GoodDetailsImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodDetailsImgMapper extends GoodDetailsImgMapperGen {

    List<GoodDetailsImg> getGoodDetailsImgs(@Param(value = "goodId")String goodId);

    int deleteImgByGoodId(@Param(value = "goodId")String goodId);

    int insertGoodDetailsImg(GoodDetailsImg goodDetailsImg);

    int deleteImg(@Param(value = "goodId")String goodId,@Param(value = "imageurl")String imageurl);

}