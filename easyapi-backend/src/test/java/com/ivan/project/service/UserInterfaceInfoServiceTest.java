package com.ivan.project.service;

import com.ivan.easyapicommon.service.InnerUserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan
 * @create 2023/4/18 15:48
 */
@SpringBootTest
class UserInterfaceInfoServiceTest {

    @Resource
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Test
    void invokeCount() {
        innerUserInterfaceInfoService.invokeCount(1,1);
    }
}