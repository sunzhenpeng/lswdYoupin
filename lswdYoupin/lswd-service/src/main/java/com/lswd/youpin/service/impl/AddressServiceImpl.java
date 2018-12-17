package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.AddressMapper;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.AssociatorAddress;
import com.lswd.youpin.model.lsyp.Address;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuhao on 2017/6/7.
 */
@Service
public class AddressServiceImpl implements AddressService {
    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public LsResponse addAddress(Address address) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (address != null) {
                log.info("{} is being executed. User = {}", "添加地址", JSON.toJSON("准备追加新的地址" + address.getCountry()
                        + address.getProvince() + address.getCity() + address.getCounty()));
                address.setUpdateTime(Dates.now());
                address.setIsDelete(false);
                Integer b = addressMapper.insertAddress(address);
                if (b!=null&&b > 0) {
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.ADDRESS_ADD_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ADDRESS_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("新建地址出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteAddress(Integer id) {

        LsResponse lsResponse = new LsResponse();
        log.info("{} is being executed. User = {}", "删除地址", JSON.toJSON("准备删除地址编号为" + id + "的地址"));
        try {
            Integer b = addressMapper.deleteAddressById(id);
            if (b!=null&&b > 0) {
                log.info("{} is being executed. User = {}", "删除地址", JSON.toJSON("成功删除地址编号为" + id + "的地址"));
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ADDRESS_DELETE_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("删除地址出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateAddress(Address address) {
        LsResponse lsResponse = new LsResponse();
        log.info("{} is being executed. User = {}", "删除地址", JSON.toJSON("准备删除地址编号为" + address.getId() + "的地址"));
        try {
            if (address != null) {
                address.setUpdateTime(Dates.now());
                Integer b = addressMapper.updateById(address);
                if (b!=null&&b > 0) {

                } else {
                    lsResponse.checkSuccess(false, CodeMessage.ADDRESS_UPDATE_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ADDRESS_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("修改地址出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAddressList(String keyword, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (keyword != null && !"".equals(keyword)) {
                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                keyword = "%" + keyword + "%";
            } else {
                keyword = "";
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            Integer total = addressMapper.getAddressCount(keyword);
            List<Address> addresses = addressMapper.selectAddressList(keyword, offSet, pageSize);
            if (addresses != null && addresses.size() > 0) {
                lsResponse.setData(addresses);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ADDRESS_NO_SELECT.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("获取地址列表出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addAssociatorAddress(AssociatorAddress address, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        Integer b = addressMapper.addAsAddress(address, associator);
        if (b != null && b > 0) {
            address.setChecked(false);
            lsResponse.checkSuccess(true, CodeMessage.ADDRESS_ADD_SUCCESS.name());
        } else {
            lsResponse.checkSuccess(false, CodeMessage.ADDRESS_ADD_ERR.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAsAddressList(Associator associator) {
        LsResponse lsResponse = new LsResponse();
        List<Address> addresses = addressMapper.selectAsAddressList(associator);
        if (addresses != null && addresses.size() > 0) {
            lsResponse.setData(addresses);
        } else {
            lsResponse.checkSuccess(false, CodeMessage.ADDRESS_NO_ERR.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateAsAddres(Associator associator, AssociatorAddress address) {
        LsResponse lsResponse = new LsResponse();
        Integer b = addressMapper.updateAsAddres(associator, address);
        if (b != null && b > 0) {
            lsResponse.checkSuccess(true, CodeMessage.ADDRESS_UPDATE_SUCCESS.name());
        } else {
            lsResponse.checkSuccess(false, CodeMessage.ADDRESS_UPDATE_ERR.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateChecked(AssociatorAddress address, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        addressMapper.updateCheckedFirst(false, associator);
        Integer b = addressMapper.updateChecked(address, associator);
        if (b != null && b > 0) {
            lsResponse.checkSuccess(true, CodeMessage.ADDRESS_UPDATE_SUCCESS.name());
        } else {
            lsResponse.checkSuccess(false, CodeMessage.ADDRESS_UPDATE_ERR.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteAsAddress(Integer id) {
        LsResponse lsResponse = new LsResponse();
        Integer b = addressMapper.deleteAsAddress(id);
        if (b != null && b > 0) {
            lsResponse.checkSuccess(true, CodeMessage.ADDRESS_DELETE_SUCCESS.name());
        } else {
            lsResponse.checkSuccess(false, CodeMessage.ADDRESS_DELETE_ERR.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getChecked(Associator associator) {
        LsResponse lsResponse=new LsResponse();
        try {
            AssociatorAddress address=addressMapper.getAddressChecked(associator);
            if(address!=null)
            {
                lsResponse.setData(address);
            }else{
                lsResponse.setData("");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }
}
