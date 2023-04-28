package com.ivan.project.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.ivan.easyapicommon.common.BaseResponse;
import com.ivan.easyapicommon.common.ErrorCode;
import com.ivan.easyapicommon.common.ResultUtils;
import com.ivan.easyapicommon.exception.BusinessException;
import com.ivan.easyapicommon.model.entity.InterfaceInfo;
import com.ivan.easyapicommon.model.entity.UserInterfaceInfo;
import com.ivan.easyapicommon.model.vo.InterfaceInfoVO;
import com.ivan.project.annotation.AuthCheck;
import com.ivan.project.mapper.UserInterfaceInfoMapper;
import com.ivan.project.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ivan
 * @create 2023/4/28 10:06
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        Map<Long, UserInterfaceInfo> userInterfaceInfoMap = userInterfaceInfos.stream().collect(Collectors.toMap(UserInterfaceInfo::getInterfaceInfoId, userInterfaceInfo -> userInterfaceInfo));
        List<Long> ids = new ArrayList<>(userInterfaceInfoMap.keySet());
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<InterfaceInfo> interfaceInfos = interfaceInfoService.list(queryWrapper);
        List<InterfaceInfoVO> interfaceInfoVOList = interfaceInfos.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtil.copyProperties(interfaceInfo, interfaceInfoVO);
            interfaceInfoVO.setTotalNum(userInterfaceInfoMap.get(interfaceInfo.getId()).getTotalNum());
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(interfaceInfoVOList)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(interfaceInfoVOList);
    }

}
