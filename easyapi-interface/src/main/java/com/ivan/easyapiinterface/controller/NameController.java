package com.ivan.easyapiinterface.controller;


import com.ivan.easyapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称API
 *
 * @author Ivan
 * @create 2023/4/17 12:23
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String name) {
        return "GET 你的名字是：" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
        return user.getName();
    }
}
