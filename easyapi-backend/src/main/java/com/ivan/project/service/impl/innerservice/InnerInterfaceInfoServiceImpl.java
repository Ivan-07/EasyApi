package com.ivan.project.service.impl.innerservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ivan.easyapicommon.model.entity.InterfaceInfo;
import com.ivan.easyapicommon.service.InnerInterfaceInfoService;
import com.ivan.project.mapper.InterfaceInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author Ivan
 * @create 2023/4/27 10:59
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterface(String url, String method) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);
        return interfaceInfo;
    }
}
