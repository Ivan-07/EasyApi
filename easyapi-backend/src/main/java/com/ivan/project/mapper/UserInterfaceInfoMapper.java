package com.ivan.project.mapper;

import com.ivan.easyapicommon.model.entity.InterfaceInfo;
import com.ivan.easyapicommon.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author maohao
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2023-04-18 11:21:17
* @Entity com.ivan.project.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

}




