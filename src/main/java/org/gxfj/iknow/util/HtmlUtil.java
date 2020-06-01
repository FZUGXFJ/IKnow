package org.gxfj.iknow.util;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author herokilito
 */
public class HtmlUtil extends HTMLEditorKit.ParserCallback{

    private static final String IMG_REGEX = "<\\\\s*img\\\\s+([^>]*)\\\\s*>";
    private static final String HTML_TAG_REGEX = "<[/]*[a-zA-Z][^>]*>";
    private static final Pattern IMG_PATTERN = Pattern.compile(IMG_REGEX);
    private static final Pattern HTML_TAG_PATTERN = Pattern.compile(HTML_TAG_REGEX);

    /**
     * 将html中的图片转化为纯文本的 '[图片]'
     * @param originHtml 要转化的html文本
     * @return 转化后的html文本
     */
    public static String changeImgTag(String originHtml) {
        Matcher matcher = IMG_PATTERN.matcher(originHtml);
        return matcher.replaceAll("[图片]");
    }

    /**
     * 将html文本中的所有Tag删除
     * @param originHtml 要转化的html文本
     * @return 转化后的html文本
     */
    public static String delHtmlTag(String originHtml) {
        Matcher matcher = HTML_TAG_PATTERN.matcher(originHtml);
        return matcher.replaceAll("");
    }


    private static HtmlUtil htmlUtil = new HtmlUtil();

    StringBuffer s;

    public void parse(String str) throws IOException {

        InputStream iin = new ByteArrayInputStream(str.getBytes());
        Reader in = new InputStreamReader(iin);
        s = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        // the third parameter is TRUE to ignore charset directive
        delegator.parse(in, this, Boolean.TRUE);
        iin.close();
        in.close();
    }

    @Override
    public void handleText(char[] text, int pos) {
        s.append(text);
    }

    public String getText() {
        return s.toString();
    }

    /**
     * 将html文本转化为纯文本
     * @param html 要转化的html文本
     * @return 纯文本
     */
    public static String html2Text(String html) {
        try {
            htmlUtil.parse(html);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return htmlUtil.getText();
    }

}
