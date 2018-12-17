package com.lswd.youpin.Thin;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.AssociatorAddress;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liuhao on 2017/6/8.
 */
public interface AddressThin {
    LsResponse deleteAddress(Integer id);

    LsResponse addAssociatorAddress(AssociatorAddress address, Associator associator);

    LsResponse getAsAddressList(Associator associator);


    LsResponse updateAsAddres(Associator associator, AssociatorAddress address);

    LsResponse updateChecked(AssociatorAddress address,Associator associator);

    LsResponse deleteAsAddress(Integer id);

    LsResponse getChecked(Associator associator);
}
