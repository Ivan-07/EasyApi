package com.ivan.project.service.impl.innerservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ivan.easyapicommon.common.ErrorCode;
import com.ivan.easyapicommon.exception.BusinessException;
import com.ivan.easyapicommon.model.entity.UserInterfaceInfo;
import com.ivan.easyapicommon.service.InnerUserInterfaceInfoService;
import com.ivan.project.mapper.UserInterfaceInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author Ivan
 * @create 2023/4/27 10:59
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        if (interfaceInfoId <=0 || userId <=0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interfaceInfoId", interfaceInfoId);
        queryWrapper.eq("userId", userId);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        userInterfaceInfo.setTotalNum(userInterfaceInfo.getTotalNum()+1);
        userInterfaceInfo.setLeftNum(userInterfaceInfo.getLeftNum()-1);
        return userInterfaceInfoMapper.updateById(userInterfaceInfo)>0;
    }
}
