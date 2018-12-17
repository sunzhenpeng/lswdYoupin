package com.lswd.youpin.service;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.AssociatorAddress;
import com.lswd.youpin.model.lsyp.Address;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liuhao on 2017/6/7.
 */
public interface AddressService {

    LsResponse addAddress(Address address);

    LsResponse deleteAddress(Integer id);

    LsResponse updateAddress(Address address);

    LsResponse getAddressList(String keyword, Integer pageNum, Integer pageSize);

    LsResponse addAssociatorAddress(AssociatorAddress address, Associator associator);

    LsResponse getAsAddressList(Associator associator);

    LsResponse updateAsAddres(Associator associator, AssociatorAddress address);

    LsResponse updateChecked(AssociatorAddress address,Associator associator);

    LsResponse deleteAsAddress(Integer id);

    LsResponse getChecked(Associator associator);
}
