package org.gxfj.iknow.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author herokilito
 */
public class HtmlUtil {

    public static String changeImgTag(String originHtml) {
        String regex = "<\\s*img\\s+([^>]*)\\s*>";
        Matcher matcher = Pattern.compile(regex).matcher(originHtml);
        return matcher.replaceAll("[图片]");
    }
}
