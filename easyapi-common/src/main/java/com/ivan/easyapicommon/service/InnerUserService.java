package com.ivan.easyapicommon.service;

import com.ivan.easyapicommon.model.entity.User;

/**
 * @author Ivan
 * @create 2023/4/27 10:22
 */
public interface InnerUserService {

    User getUserByAk(String accessKey);
}
