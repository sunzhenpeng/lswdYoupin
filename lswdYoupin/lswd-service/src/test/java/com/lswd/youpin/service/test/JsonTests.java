package com.lswd.youpin.service.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * Created by guolanrui on 16/9/26.
 */
public class JsonTests {

    @Test
    public void JsonTest() {
        ChildModel childModel = new ChildModel();
        childModel.setName("sadfasfa");
        System.out.println(JSON.toJSONString(childModel));
    }

    class FatherModel {
        private String client = "123213";
        private String password = "1232131";

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    class ChildModel extends FatherModel {

        private String user;
        private String name;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
