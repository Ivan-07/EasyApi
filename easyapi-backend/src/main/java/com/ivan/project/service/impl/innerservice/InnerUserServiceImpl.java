package com.ivan.project.service.impl.innerservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ivan.easyapicommon.model.entity.User;
import com.ivan.easyapicommon.service.InnerUserService;
import com.ivan.project.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author Ivan
 * @create 2023/4/27 11:00
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByAk(String accessKey) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}
