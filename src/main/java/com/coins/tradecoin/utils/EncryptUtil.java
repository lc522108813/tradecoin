package com.coins.tradecoin.utils;


import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * 加密工具类
 * @author coins
 */
@Slf4j
public class EncryptUtil {

    /**
     * 使用hmacSha256进行加密
     **/
    public static String hmacSha256(String message, String secretKey) {
        String hash = "";
        try {
            log.info("the message is {}", message);
            Mac sha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            sha256.init(secretKeySpec);
            byte[] bytes = sha256.doFinal(message.getBytes());
            hash = bytes2Base64(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;

    }

    /**
     * base64编码
     **/
    public static String bytes2Base64(byte[] bytes) {
        String basicEncoded = Base64.getEncoder().encodeToString(bytes);
        return basicEncoded;
    }

    /**
     * 将字节数组转换为16进制的字符串
     **/
    public static String bytes2Hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            String tmp = Integer.toHexString(b);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase();
    }


}
