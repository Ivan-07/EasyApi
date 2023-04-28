package com.ivan.easyapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名工具
 * @author Ivan
 * @create 2023/4/17 17:27
 */
public class SignUtils {

    public static String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body+"."+ secretKey;
        return md5.digestHex(content);
    }
}
