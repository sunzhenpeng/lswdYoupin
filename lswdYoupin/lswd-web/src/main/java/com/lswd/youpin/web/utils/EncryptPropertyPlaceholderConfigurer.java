package com.lswd.youpin.web.utils;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Created by liruilong on 2017/12/13.
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
{
    private String[] encryptPropNames = {"jdbc.username", "jdbc.password","jdbc_username","jdbc_password"};

    @Override
    protected String convertProperty(String propertyName, String propertyValue)
    {

        //如果在加密属性名单中发现该属性
        if (isEncryptProp(propertyName))
        {
            String decryptValue = DESUtils.getDecryptString(propertyValue);
            return decryptValue;
        }else {
            return propertyValue;
        }

    }

    private boolean isEncryptProp(String propertyName)
    {
        for (String encryptName : encryptPropNames)
        {
            if (encryptName.equals(propertyName))
            {
                return true;
            }
        }
        return false;
    }
}