package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.CounterMenuMapperGen;
import com.lswd.youpin.model.lsyp.CounterMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounterMenuMapper extends CounterMenuMapperGen {

    Integer getCounterMenuListCount(@Param(value = "keyword")String keyword,@Param(value = "counterId")String counterId,
                                    @Param(value = "menutypeId")Integer menutypeId);

    List<CounterMenu> getCounterMenuList(@Param(value = "keyword")String keyword,@Param(value = "counterId")String counterId,
                                         @Param(value = "menutypeId")Integer menutypeId,@Param(value = "offSet")Integer offSet,@Param(value = "pageSize")Integer pageSize);

    Integer deleteCounterMenuUpdate(@Param(value = "id")Integer id);

    Integer getMaxId();

    Integer getBingMenuListCount(@Param(value = "keyword")String keyword, @Param(value = "menutypeId")Integer menutypeId);

    List<CounterMenu> getBingMenuList(@Param(value = "keyword")String keyword, @Param(value = "menutypeId")Integer menutypeId,@Param(value = "offSet")Integer offSet,@Param(value = "pageSize")Integer pageSize);

    Integer getCounterMenuLinkedListCount(@Param(value = "counterId")String counterId,@Param(value = "keyword")String keyword,@Param(value = "menutypeId")Integer menutypeId);

    List<CounterMenu> getCounterMenuLinkedList(@Param(value = "counterId")String counterId,@Param(value = "keyword")String keyword,@Param(value = "menutypeId")Integer menutypeId,
                                               @Param(value = "offSet")Integer offSet,@Param(value = "pageSize")Integer pageSize);

    List<CounterMenu> getCounterMenuByTypeId(@Param(value = "typeId")Integer typeId,@Param(value = "counterId")String counterId);


    Integer getCounterMenuListBTSYCount(@Param(value = "keyword")String keyword,@Param(value = "counterId")String counterId,
                                        @Param(value = "menutypeId")Integer menutypeId);

    List<CounterMenu> getCounterMenuListBTSY(@Param(value = "keyword")String keyword,@Param(value = "counterId")String counterId,
                                             @Param(value = "menutypeId")Integer menutypeId,@Param(value = "offSet")Integer offSet,@Param(value = "pageSize")Integer pageSize);


}