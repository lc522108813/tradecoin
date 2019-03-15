package com.leichuang.tradecoin.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encrypt {

    private static final String ENCODE = "UTF-8";
    private static final String ENCNAME = "MD5";


    /** 获取到md5加密后的密文 **/
    public String getMessageDigest(String src) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytes = src.getBytes(ENCODE);
        MessageDigest md = MessageDigest.getInstance(ENCNAME);
        md.update(bytes);
        String strDes = bytes2Hex(md.digest());
        return strDes;

    }

    /**
     * 将字节数组转换为16进制表示的字符串
     **/
    public static String bytes2Hex(byte[] bytes) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bytes.length; i++) {
            tmp = (Integer.toHexString(bytes[i]));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;

    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String sre = "*LS).S}";
        byte[] bytes = sre.getBytes(ENCODE);
        for (byte b : bytes) {
            System.out.println(Integer.toHexString(b));
            String tmp = (Integer.toHexString(b & 0xff));
            System.out.println(tmp);
        }
    }
}
