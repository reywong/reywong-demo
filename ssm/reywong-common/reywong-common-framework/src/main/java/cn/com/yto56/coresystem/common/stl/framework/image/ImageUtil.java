package cn.com.yto56.coresystem.common.stl.framework.image;

import cn.com.yto56.coresystem.common.stl.framework.base.StringUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtil {
    private static Logger logger = Logger.getLogger(ImageUtil.class);

    /**
     * 按图片流文件生成图片
     *
     * @param path           图片路径
     * @param streamFileData BASE64 加密图片
     * @param imageName      生成图片名称
     * @return 图片存放路径
     */
    public static String buildImageByStreamFile(String path, String streamFileData, String imageName) {
        String imageURLResult = "";
        SimpleDateFormat sdfPath = new SimpleDateFormat("yyyyMMdd");
        String updateTime = sdfPath.format(new Date());
        path = path.endsWith("/") ? path : path + "/";
        String initPath = path + File.separator + updateTime + File.separator;
        String initURL = initPath + imageName;
        try {
            byte[] imageByteData = StringUtils.base64Decode(streamFileData);
            try {
                File dir = new File(initPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File imageFile = new File(initURL);
                FileImageOutputStream imageOutput = new FileImageOutputStream(imageFile);
                imageOutput.write(imageByteData, 0, imageByteData.length);
                imageOutput.close();
                imageURLResult = imageFile.getPath();
            } catch (Exception ex) {
                logger.error("Exception: " + ex);
            }
        } catch (Exception e) {
            logger.error("解析BASE64图片流文件出错！", e);
        }
        return imageURLResult;
    }

    /**
     * base64转换成流
     *
     * @param base64string
     * @return
     */
    public static InputStream baseToInputStream(String base64string) {
        ByteArrayInputStream stream = null;
        try {
            byte[] bytes1 = StringUtils.base64Decode(base64string);
            stream = new ByteArrayInputStream(bytes1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }
    /**
     * base64转换bufferedImage
     *
     * @param base64string
     * @return
     */
    public static BufferedImage baseToBufferedImage(String base64string) throws IOException {
        BufferedImage image = null;
        InputStream stream = baseToInputStream(base64string);
        image = ImageIO.read(stream);
        return image;
    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param inputStream 图片地址
     * @return
     */
    public static String imgPathToBase64(InputStream inputStream) {
        String result = "";
        if (inputStream != null) {
            byte[] data = null;
            //读取图片字节数组
            try {
                data = new byte[inputStream.available()];
                inputStream.read(data);
                inputStream.close();
                //对字节数组Base64编码
                result = StringUtils.base64Encode(data);
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return result;
    }

    /**
     * 图片转换成base64
     * @param imgUrl
     * @return
     */
    public static String imgPathToBase64(String imgUrl) {
        String result = "";
        File file = new File(imgUrl);
        if (file.exists()) {
            InputStream in = null;
            byte[] data = null;
            //读取图片字节数组
            try {
                in = new FileInputStream(imgUrl);
                data = new byte[in.available()];
                in.read(data);
                in.close();
                //对字节数组Base64编码
                result = StringUtils.base64Encode(data);
            } catch (IOException e) {
                logger.error(e);
            }

        }
        return result;//返回Base64编码过的字节数组字符串
    }


    /**
     * 像素点转换
     *
     * @param bufferedImage
     * @return
     */
    public static int[] bufferedImageToInts(BufferedImage bufferedImage) {
        int[] result = null;
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        result = new int[width * height];
        try {
            new PixelGrabber(bufferedImage.getSource(), 0, 0, width, height, result, 0, width).grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 像素点转换
     *
     * @param pixels
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage intsToBufferedImage(int[] pixels, int width, int height) {
        BufferedImage bufferedImage = null;
        ImageProducer imageProducer = new MemoryImageSource(width, height, pixels, 0, width);
        Image image = Toolkit.getDefaultToolkit().createImage(imageProducer);
        bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        return bufferedImage;
    }

    /**
     * 画直线
     *
     * @param bufferedImage
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return
     */
    public static BufferedImage drawLine(BufferedImage bufferedImage, int startX, int startY, int endX, int endY) {
        BufferedImage result = bufferedImage;
        Graphics2D g = result.createGraphics();
        g.drawImage(result.getScaledInstance(result.getWidth(null), result.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
        g.setPaint(Color.RED);
        g.drawLine(startX, startY, endX, endY);
        return result;
    }

    public static int clamp(int value) {
        return value > 255 ? 255 :
                (value < 0 ? 0 : value);
    }


}
