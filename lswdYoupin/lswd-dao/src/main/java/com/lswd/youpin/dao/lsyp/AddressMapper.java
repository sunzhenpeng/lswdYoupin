package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.AddressMapperGen;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.AssociatorAddress;
import com.lswd.youpin.model.lsyp.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper extends AddressMapperGen {

    Integer insertAddress(Address address);

    Integer deleteAddressById(@Param(value = "id") Integer id);

    Integer updateById(Address address);

    Integer getAddressCount(@Param(value = "keyword") String keyword);

    List<Address> selectAddressList(@Param(value = "keyword") String keyword,@Param(value = "offSet") int offSet, @Param(value = "pageSize") Integer pageSize);

    Integer addAsAddress(@Param(value = "a") AssociatorAddress address, @Param(value = "associator") Associator associator);

    List<Address> selectAsAddressList(Associator associator);

    Integer updateAsAddres( @Param(value = "associator")Associator associator, @Param(value = "a")AssociatorAddress address);

    Integer updateCheckedFirst(@Param(value = "flag") Boolean flag,@Param(value = "associator") Associator associator);

    Integer updateChecked(@Param(value = "a") AssociatorAddress address, @Param(value = "associator") Associator associator);

    Integer deleteAsAddress(@Param(value = "id") Integer id);

    AssociatorAddress getAddressChecked(Associator associator);
}