package cn.com.yto56.coresystem.common.stl.framework.http;

import cn.com.yto56.coresystem.common.stl.framework.base.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wangrui on 2016/4/1.
 */
public class HttpUtil {
    private static final Logger logger = Logger.getLogger(HttpUtil.class);

    /**
     * 发送json
     *
     * @param json
     * @param urlStr
     * @return
     */
    public static String httpClient(String json, String urlStr) {
        String result = "";
        URL url = null;
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        String line = "";
        InputStreamReader isr = null;
        InputStream is = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-type", "application/json;text/html");
            con.setRequestProperty("Accept-Charset", "utf-8");
            con.setRequestProperty("contentType", "utf-8");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(json);
            out.flush();
            out.close();
            is = con.getInputStream();
            isr = new InputStreamReader(is, "UTF-8");
            reader = new BufferedReader(isr);
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            result = sb.toString();
            is.close();
            isr.close();
            reader.close();
        } catch (MalformedURLException e) {
            logger.error("URL错误", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持该编码", e);
        } catch (IOException e) {
            logger.error("服务器地址不存在或者服务器已经关闭", e);
        }
        return result;
    }


    /**
     * URL编码（utf-8）
     *
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        try {
            String ip = request.getHeader("X-Forwarded-For");
            if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
                //多次反向代理后会有多个ip值，第一个ip才是真实ip
                int index = ip.indexOf(",");
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            }
            ip = request.getHeader("X-Real-IP");
            if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return request.getRemoteAddr();
    }


}
