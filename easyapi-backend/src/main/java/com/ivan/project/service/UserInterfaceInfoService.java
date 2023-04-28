package com.ivan.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ivan.easyapicommon.common.BaseResponse;
import com.ivan.easyapicommon.model.entity.UserInterfaceInfo;

/**
* @author maohao
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2023-04-18 11:21:17
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

}
