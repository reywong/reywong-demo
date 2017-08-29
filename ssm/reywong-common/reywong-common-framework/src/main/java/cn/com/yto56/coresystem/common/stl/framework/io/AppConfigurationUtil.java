package cn.com.yto56.coresystem.common.stl.framework.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wangrui on 2016/3/2.
 */
public class AppConfigurationUtil {
    private static Logger logger = LoggerFactory.getLogger(AppConfigurationUtil.class);

    private static Properties prop = new Properties();

    static {
        InputStream is = AppConfigurationUtil.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            prop.load(is);
        } catch (IOException e) {
            logger.error("[error]Could not load appConfiguration.properties", e);
        }
    }

    public static String getPropertyValue(String key) {
        return getPropertyValue(key, null);
    }

    public static String getPropertyValue(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    public static String getUrl(String fileName) {
        String path = "";
        try {
            path = AppConfigurationUtil.class.getClassLoader().getResource(fileName).toString().substring(6);
            path = path.replace("%20", " ");// 如果你的文件路径中包含空格，是必定会报错的
        } catch (Exception e) {
            path = System.getProperty("user.dir") + "/" + fileName;
        }

        //linux下系统
        if (path != null) {
            if (!path.contains(":") && !path.startsWith("/")) {
                path = "/" + path;
            }
        }
        return path;
    }
}
