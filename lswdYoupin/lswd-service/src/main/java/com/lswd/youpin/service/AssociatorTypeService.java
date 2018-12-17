package com.lswd.youpin.service;


import com.lswd.youpin.model.AssociatorType;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

public interface AssociatorTypeService {

    LsResponse getAssociatorTypeList(String name);

    LsResponse addAssociatorType(AssociatorType associatorType, User user);

    LsResponse updateAssociatorType(AssociatorType associatorType, User user);
}
