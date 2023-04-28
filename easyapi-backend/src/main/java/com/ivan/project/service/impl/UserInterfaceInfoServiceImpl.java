package com.ivan.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.easyapicommon.common.ErrorCode;
import com.ivan.easyapicommon.exception.BusinessException;
import com.ivan.project.mapper.UserInterfaceInfoMapper;
import com.ivan.easyapicommon.model.entity.UserInterfaceInfo;
import com.ivan.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

/**
 * @author maohao
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
 * @createDate 2023-04-18 11:21:17
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {
    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long userId = userInterfaceInfo.getUserId();
        Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
        Integer totalNum = userInterfaceInfo.getTotalNum();
        Integer leftNum = userInterfaceInfo.getLeftNum();
        // 创建时，所有参数必须非空
        if (add) {
            if (userId <=0 || interfaceInfoId <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }
        if (leftNum < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }
    }
}
