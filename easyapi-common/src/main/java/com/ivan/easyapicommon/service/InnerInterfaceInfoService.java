package com.ivan.easyapicommon.service;

import com.ivan.easyapicommon.model.entity.InterfaceInfo;

/**
 * @author Ivan
 * @create 2023/4/27 10:22
 */
public interface InnerInterfaceInfoService {

    InterfaceInfo getInterface(String url, String method);

}
