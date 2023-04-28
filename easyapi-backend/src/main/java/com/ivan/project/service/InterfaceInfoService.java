package com.ivan.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ivan.easyapicommon.common.BaseResponse;
import com.ivan.easyapicommon.model.entity.InterfaceInfo;

/**
* @author maohao
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-04-16 23:26:03
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);


}
