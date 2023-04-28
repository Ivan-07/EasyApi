package com.ivan.easyapicommon.model.vo;

import com.ivan.easyapicommon.model.entity.InterfaceInfo;
import lombok.Data;

/**
 * @author Ivan
 * @create 2023/4/28 11:52
 */
@Data
public class InterfaceInfoVO extends InterfaceInfo {

    private Integer totalNum;

    private static final long serialVersionUID = 1L;
}
