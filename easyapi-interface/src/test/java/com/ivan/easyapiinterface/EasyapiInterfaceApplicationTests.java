package com.ivan.easyapiinterface;

import com.ivan.easyapiclientsdk.client.EasyApiClient;
import com.ivan.easyapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class EasyapiInterfaceApplicationTests {

    @Resource
    private EasyApiClient easyApiClient;

    @Test
    void contextLoads() {
        String result1 = easyApiClient.getNameByGet("ivan1");
        User user = new User();
        user.setName("ivan2");
        String result2 = easyApiClient.getUsernameByPost(user);
        System.out.println(result1);
        System.out.println(result2);
    }

}
