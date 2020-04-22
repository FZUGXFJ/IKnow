package org.gxfj.iknow.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

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

    public static boolean sh1Compare(String plainText,String cypherText) {
        return cypherText.equals(sh1(plainText));
    }

    public static String generatorVerifyCode(int codeLength) {
        StringBuilder code = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0;i < codeLength;i ++) {
            code.append(random.nextInt(9));
        }
        return code.toString();
    }
}
