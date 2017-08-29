package cn.com.yto56.coresystem.common.stl.framework.crypt;

import cn.com.yto56.coresystem.common.stl.framework.base.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created by wangrui on 2017/8/11.
 */
public class CryptUtil {
    /**
     * 签名key
     */
    private static final String KEY = "reywong";
    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    /*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private static String ivParameter = "0392039203920300";

    /**
     * 校验签名
     *
     * @param sign   签名
     * @param map    数据
     * @param rsaKey 签名key
     * @return
     */
    public static boolean checkSign(String sign, Map map, String rsaKey) {
        boolean result = false;
        SortedMap<Object, Object> sortedMap = new TreeMap<Object, Object>();
        if (map instanceof Map) {
            Set set = map.keySet();
            Iterator it = set.iterator();
            for (; it.hasNext(); ) {
                String key = (String) it.next();
                sortedMap.put(key, map.get(key));
            }
        }
        String signKey = createSign(sortedMap, rsaKey);
        if (sign.equals(signKey)) {
            result = true;
        }
        return result;
    }

    /**
     * 签名算法sign
     *
     * @param parameters 参数
     * @param rsaKey     签名key
     * @return
     */
    public static String createSign(SortedMap<Object, Object> parameters, String rsaKey) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + rsaKey);
        String characterEncoding = "UTF-8";
        String sign = MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    /**
     * 校验签名
     *
     * @param sign
     * @param map
     * @return
     */
    public static boolean checkSign(String sign, Map map) {
        boolean result = false;
        SortedMap<Object, Object> sortedMap = new TreeMap<Object, Object>();
        if (map instanceof Map) {
            Set set = map.keySet();
            Iterator it = set.iterator();
            for (; it.hasNext(); ) {
                String key = (String) it.next();
                sortedMap.put(key, map.get(key));
            }
        }
        String signKey = createSign(sortedMap);
        if (sign.equals(signKey)) {
            result = true;
        }
        return result;
    }

    /**
     * 签名算法sign
     *
     * @param parameters
     * @return
     */
    public static String createSign(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + KEY);
        String characterEncoding = "UTF-8";
        String sign = MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }


    /**
     * 加密算法
     *
     * @param encData   明文
     * @param secretKey 秘钥
     * @return
     * @throws Exception
     */
    public static String encrypt(String encData, String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
        return Base64.encode(encrypted);// 此处使用BASE64做转码。
    }

    /**
     * 解密
     *
     * @param decData   密文
     * @param secretKey 秘钥
     * @return
     * @throws Exception
     */
    public static String decrypt(String decData, String secretKey) throws Exception {
        byte[] raw = secretKey.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = Base64.decode(decData);// 先用base64解密
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original, "utf-8");
        return originalString;
    }


    /**
     * 加密
     *
     * @param encData   明文
     * @param secretKey 秘钥
     * @param ivs       加密向量
     * @return
     * @throws Exception
     */
    public static String encrypt(String encData, String secretKey, String ivs) throws Exception {
        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != 16) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
        return Base64.encode(encrypted);// 此处使用BASE64做转码。
    }

    /**
     * 解码
     *
     * @param decData   密文
     * @param secretKey 秘钥
     * @param ivs       解密向量
     * @return
     * @throws Exception
     */
    public String decrypt(String decData, String secretKey, String ivs) throws Exception {
        try {
            byte[] raw = secretKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(decData);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }


    public static void main(String[] args) throws Exception {
        String key = "reywong";
        SortedMap sortedMap = new TreeMap();
        sortedMap.put("vip_userid", "0001");
        sortedMap.put("einvoice_invoice_page", "1");
        sortedMap.put("einvoice_invoice_page", "einvoice_invoice_pagesize");
        System.out.println(CryptUtil.createSign(sortedMap, key));

    }
}
