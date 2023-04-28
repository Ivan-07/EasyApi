package com.ivan.easyapigateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.easyapiclientsdk.utils.SignUtils;
import com.ivan.easyapicommon.common.BaseResponse;
import com.ivan.easyapicommon.common.ErrorCode;
import com.ivan.easyapicommon.common.ResultUtils;
import com.ivan.easyapicommon.model.entity.InterfaceInfo;
import com.ivan.easyapicommon.model.entity.User;
import com.ivan.easyapicommon.service.InnerInterfaceInfoService;
import com.ivan.easyapicommon.service.InnerUserInterfaceInfoService;
import com.ivan.easyapicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    private static List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    private static final String INTERFACE_HOST = "http://localhost:8123";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getRemoteAddress().getHostString();
        String method = request.getMethod().toString();
        String path = INTERFACE_HOST+request.getPath().value();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求地址：" + request.getRemoteAddress());
        log.info("请求路径：" + request.getPath());
        log.info("请求参数：" + request.getQueryParams());
        log.info("请求方法：" + method);
        log.info("请求来源地址：" + url);
        // 2. 访问控制 - 黑白名单
        ServerHttpResponse response = exchange.getResponse();
        if (!IP_WHITE_LIST.contains(request.getRemoteAddress().getHostString())) {
            return errorResponse(response, ErrorCode.NO_AUTH_ERROR);
        }
        // 3. 权限判断
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String body = headers.getFirst("body");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        User user = innerUserService.getUserByAk(accessKey);
        if (user == null) {
            return errorResponse(response, ErrorCode.PARAMS_ERROR);
        }
        String realSecretKey = user.getSecretKey();
        if (Long.parseLong(nonce) > 10000) {
            return errorResponse(response, ErrorCode.NO_AUTH_ERROR);
        }
        long currentTimestamp = System.currentTimeMillis() / 1000;
        long FIVE_MINUTE = 60 * 5L;
        if (timestamp == null || currentTimestamp - Long.parseLong(timestamp) >= FIVE_MINUTE) {
            return errorResponse(response, ErrorCode.NO_AUTH_ERROR);
        }
        String realSign = SignUtils.genSign(body, realSecretKey);
        if (!realSign.equals(sign)) {
            return errorResponse(response, ErrorCode.NO_AUTH_ERROR);
        }
        // 4. 请求的模拟接口是否存在
        InterfaceInfo interfaceInfo = innerInterfaceInfoService.getInterface(path, method);
        if (interfaceInfo == null) {
            return errorResponse(response, ErrorCode.PARAMS_ERROR);
        }
        // 5. 请求转发，调用模拟接口
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpHeaders newHeaders = new HttpHeaders();
            newHeaders.setContentType(MediaType.APPLICATION_JSON);
            if (response.getStatusCode()==HttpStatus.OK) {
                // 6. 响应日志
                log.info("响应："+response.getStatusCode());
                // 7. 接口调用次数+1
                try {
                    innerUserInterfaceInfoService.invokeCount(interfaceInfo.getId(), user.getId());
                } catch (Exception e) {
                    log.error("invokeCount error");
                }
            }else {
                errorResponse(response, ErrorCode.PARAMS_ERROR);
            }
        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> errorResponse(ServerHttpResponse response, ErrorCode errorCode) {
        BaseResponse error = ResultUtils.error(errorCode);
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        DataBufferFactory bufferFactory = response.bufferFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        // 要写入的数据对象，会自动转为json格式
        DataBuffer wrap = null;
        try {
            wrap = bufferFactory.wrap(objectMapper.writeValueAsBytes(error));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        DataBuffer finalWrap = wrap;
        response.writeWith(Mono.fromSupplier(() -> finalWrap));
        return response.setComplete();
    }

//    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
//        response.setStatusCode(HttpStatus.FORBIDDEN);
//        return response.setComplete();
//    }
//
//    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
//        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
//        return response.setComplete();
//    }
}