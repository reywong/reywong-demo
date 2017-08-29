package cn.com.yto56.coresystem.common.stl.framework.base;


import org.apache.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final Logger logger = Logger.getLogger(StringUtils.class);


    public static List getPhoneNo(String srcText) {
        List result = new ArrayList();
        if (srcText != null && !srcText.trim().equals("")) {
            String[] temps = null;
            srcText = srcText.trim();
            temps = srcText.split("\\n+|\\r\\n+|\\r+");
            if (temps != null && temps.length > 0) {
                for (int i = 0; i < temps.length; i++) {
                    String temp = temps[i];
                    List phoneNo = checkPhone(temp);
                    if (phoneNo.size() > 0) {
                        result.add(phoneNo.get(0));
                    }
                }
            }
        }
        return result;
    }

    public static List checkPhone(String str) {
        List result = new ArrayList();
        str = str.replace("o", "0").replace("O", "0").replace("l", "1").replace("i", "1");
        Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[34578]\\d{9})|(?:861[34578]\\d{9}))(?!\\d)");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    /**
     * 判断字符串是否不为空或空字符串
     *
     * @param str 字符串
     * @return
     */
    public static boolean isNotBlank(Object str) {
        boolean result = false;
        if (str != null && !"".equals(str)) {
            result = true;
        }
        return result;
    }

    public static boolean isNotEmpty(Object str) {
        return isNotBlank(str);
    }

    /**
     * 判断字符串是否为空或空字符串
     *
     * @param str 字符串
     * @return
     */
    public static boolean isBlank(Object str) {
        boolean result = false;
        if (str == null || "".equals(str)) {
            result = true;
        }
        return result;
    }

    public static boolean isEmpty(Object str) {
        return isBlank(str);
    }

    public static String firstToLowerCase(String str) {
        return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toLowerCase());
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String parsePropertiesKey(String value) {
        if (isEmpty(value)) {
            return null;
        }

        if (value.startsWith("${") && value.endsWith("}")) {
            return value.replace("${", "").replace("}", "");
        }
        return null;
    }

    /**
     * 对html标签进行转换
     *
     * @param txt
     * @return
     */
    public static String toTextForHtml(String txt) {
        if (null == txt) {
            return "";
        }
        txt = txt.replaceAll("&", "&amp;");
        txt = txt.replaceAll("<", "&lt;");
        txt = txt.replaceAll(">", "&gt;");
        txt = txt.replaceAll("\"", "&quot;");
        txt = txt.replaceAll("'", "&#146;");
        return txt;
    }
    /**
     * 对html标签进行转换
     *
     * @param <T>
     * @param txt
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> void toTextForHtml(Collection<T> txt) {
        List list = new ArrayList();
        if (txt != null) {
            for (Iterator iterator = txt.iterator(); iterator.hasNext();) {
                Object object = (Object) iterator.next();
                toTextForHtml(object.toString());
                list.add(object);
            }
            txt = list;
        }
    }

    public static String firstToUpperCase(String str) {
        return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
    }

    /**
     * 将数组转化成"|"分隔的字符串
     *
     * @param datas 字符串数组
     * @return
     */
    public static String stringsToString(String[] datas) {
        String result = "";
        if (datas != null && datas.length > 0) {
            StringBuffer sb = new StringBuffer();
            int t = datas.length;
            for (int i = 0; i < t; i++) {
                if (i != t - 1) {
                    sb.append(datas[i] + "|");
                } else {
                    sb.append(datas[i]);
                }
            }
            result = sb.toString();
        }
        return result;
    }

    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes) {
        return Base64.encode(bytes);
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception {
        return StringUtils.isBlank(base64Code) ? null : Base64.decode(base64Code);
    }

}