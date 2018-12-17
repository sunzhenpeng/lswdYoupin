package com.lswd.youpin.Thin;

import com.lswd.youpin.model.AssociatorType;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletRequest;

public interface AssociatorTypeThin {

    LsResponse getAssociatorTypeList(String name);

    LsResponse addAssociatorType(AssociatorType associatorType, User user);

    LsResponse updateAssociatorType(AssociatorType associatorType, User user);

}
