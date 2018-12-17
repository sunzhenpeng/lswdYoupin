package com.lswd.youpin.service.impl;

import com.lswd.youpin.dao.RecordInfoMapper;
import com.lswd.youpin.model.RecordInfo;
import com.lswd.youpin.service.RecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by H61M-K on 2018/2/7.
 */
@Service
public class RecordInfoServiceImpl implements RecordInfoService {
    @Autowired
    private RecordInfoMapper recordInfoMapper;

    @Override
    public List<RecordInfo> getAll() {
        List<RecordInfo> list = null;
        try {
            list=recordInfoMapper.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
