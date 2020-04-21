package org.gxfj.iknow.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author herokilito
 * 基于apache 的 commons codoc的安全工具类
 */
public class SecurityUtil {

    public static String md5(String plainText) {
        return DigestUtils.md5Hex(plainText.getBytes());
    }

    public static boolean md5Compare(String plainText,String cypherText) {
        return cypherText.equals(md5(plainText));
    }

    public static String sh1(String plainText) {
        return DigestUtils.sha1Hex(plainText.getBytes());
    }
}
