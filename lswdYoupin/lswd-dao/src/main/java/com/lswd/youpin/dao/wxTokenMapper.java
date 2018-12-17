package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.wxTokenMapperGen;
import com.lswd.youpin.model.wxToken;

public interface wxTokenMapper extends wxTokenMapperGen {
    wxToken selectByName(String name);
}