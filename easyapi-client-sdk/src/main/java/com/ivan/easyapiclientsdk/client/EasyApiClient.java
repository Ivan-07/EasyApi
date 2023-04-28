package com.ivan.easyapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ivan.easyapiclientsdk.model.User;

import java.util.HashMap;
import java.util.Map;

import static com.ivan.easyapiclientsdk.utils.SignUtils.genSign;

/**
 * @author Ivan
 * @create 2023/4/17 15:56
 */
public class EasyApiClient {

    private String accessKey;
    private String secretKey;

    private static final String HOST = "http://localhost:8090";

    public EasyApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.get(HOST+"/api/name/", paramMap);
        System.out.println(result);
        return "GET 你的名字是：" + result;
    }

    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.post(HOST+"/api/name/", paramMap);
        return "POST 你的名字是：" + result;
    }

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> map = new HashMap<>();
        map.put("accessKey", accessKey);
        map.put("nonce", RandomUtil.randomNumbers(4));
        map.put("body", body);
        map.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
        map.put("sign", genSign(body, secretKey));
        return map;
    }

    public String getUsernameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse response = HttpRequest.post(HOST+"/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        String result = response.body();
        System.out.println(result);
        return "POST 你的名字是：" + result;
    }
}
