package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.AddressThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.AssociatorAddress;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AddressService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by liuhao on 2017/6/8.
 */
@Component
public class AddressThinImpl implements AddressThin{
    private final Logger log = LoggerFactory.getLogger(AddressThinImpl.class);
    @Autowired
    private AddressService addressService;
    @Override
    public LsResponse deleteAddress(Integer id) {
        LsResponse lsResponse=null;
        try {
            lsResponse = new LsResponse();
            lsResponse=addressService.deleteAddress(id);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addAssociatorAddress(AssociatorAddress address, Associator associator) {
        LsResponse lsResponse=new LsResponse();
        try {
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType()))
            {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            System.out.println(associator.getAssociatorId()+"----------------");
            lsResponse= addressService.addAssociatorAddress(address,associator);
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAsAddressList(Associator associator) {
        LsResponse lsResponse=new LsResponse();
        try {
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType()))
            {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse=addressService.getAsAddressList(associator);
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateAsAddres(Associator associator, AssociatorAddress address) {
        LsResponse lsResponse=new LsResponse();
        try {
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType()))
            {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse=addressService.updateAsAddres(associator,address);
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateChecked(AssociatorAddress address,Associator associator) {
        LsResponse lsResponse=new LsResponse();
        try {
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType()))
            {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse=addressService.updateChecked(address,associator);
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteAsAddress(Integer id) {
        LsResponse lsResponse=new LsResponse();
        try {
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType()))
            {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse=addressService.deleteAsAddress(id);
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getChecked(Associator associator) {
        LsResponse lsResponse=new LsResponse();
        try {
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType()))
            {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse=addressService.getChecked(associator);
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }
}
