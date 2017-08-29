package cn.com.yto56.coresystem.common.stl.framework.pdf;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PdfConvertTool {
    private static final Logger logger = LoggerFactory.getLogger(PdfConvertTool.class);

    /**
     * 获取格式化后的时间信息
     *
     * @param calendar 时间信息
     * @return
     */
    public static String dateFormat(Calendar calendar) {
        if (null == calendar) {
            return null;
        }
        String date = null;
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        date = format.format(calendar.getTime());
        return date == null ? "" : date;
    }

    /**
     * 打印纲要
     **/
    public static void getPDFOutline(String pdfFilename) {
        try {
            //加载 pdf 文档,获取PDDocument文档对象
            PDDocument document = PDDocument.load(new File(pdfFilename));
            //获取PDDocumentCatalog文档目录对象
            PDDocumentCatalog catalog = document.getDocumentCatalog();
            //获取PDDocumentOutline文档纲要对象
            PDDocumentOutline outline = catalog.getDocumentOutline();
            //获取第一个纲要条目（标题1）
            PDOutlineItem item = outline.getFirstChild();
            if (outline != null) {
                //遍历每一个标题1
                while (item != null) {
                    //打印标题1的文本
                    System.out.println("Item:" + item.getTitle());
                    //获取标题1下的第一个子标题（标题2）
                    PDOutlineItem child = item.getFirstChild();
                    //遍历每一个标题2
                    while (child != null) {
                        //打印标题2的文本
                        System.out.println("    Child:" + child.getTitle());
                        //指向下一个标题2
                        child = child.getNextSibling();
                    }
                    //指向下一个标题1
                    item = item.getNextSibling();
                }
            }
            //关闭输入流
            document.close();
        } catch (FileNotFoundException ex) {
            logger.error("文件不存在", ex);
        } catch (IOException ex) {
            logger.error("文件读取失败", ex);
        }
    }

    /**
     * 打印一级目录
     **/
    public static void getPDFCatalog(String pdfFilename) {
        try {
            //加载 pdf 文档,获取PDDocument文档对象
            PDDocument document = PDDocument.load(new File(pdfFilename));
            //获取PDDocumentCatalog文档目录对象
            PDDocumentCatalog catalog = document.getDocumentCatalog();
            //获取PDDocumentOutline文档纲要对象
            PDDocumentOutline outline = catalog.getDocumentOutline();
            //获取第一个纲要条目（标题1）
            if (outline != null) {
                PDOutlineItem item = outline.getFirstChild();
                //遍历每一个标题1
                while (item != null) {
                    //打印标题1的文本
                    System.out.println("Item:" + item.getTitle());
                    //指向下一个标题1
                    item = item.getNextSibling();
                }
            }
            //关闭输入流
            document.close();
        } catch (FileNotFoundException ex) {
            logger.error("文件不存在", ex);
        } catch (IOException ex) {
            logger.error("文件读取失败", ex);
        }
    }

    /**
     * 获取PDF文档元数据
     **/
    public static PDDocumentInformation getPDFInformation(String pdfFilename) {
        PDDocumentInformation info = null;
        PDDocument document = null;
        try {
            //加载 pdf 文档,获取PDDocument文档对象
            document = PDDocument.load(new File(pdfFilename));
            /** 文档属性信息 **/
            info = document.getDocumentInformation();
//            System.out.println("页数:" + document.getNumberOfPages());
//            System.out.println("标题:" + info.getTitle());
//            System.out.println("主题:" + info.getSubject());
//            System.out.println("作者:" + info.getAuthor());
//            System.out.println("关键字:" + info.getKeywords());
//            System.out.println("应用程序:" + info.getCreator());
//            System.out.println("pdf 制作程序:" + info.getProducer());
//            System.out.println("Trapped:" + info.getTrapped());
//            System.out.println("创建时间:" + dateFormat(info.getCreationDate()));
//            System.out.println("修改时间:" + dateFormat(info.getModificationDate()));
        } catch (FileNotFoundException ex) {
            logger.error("文件不存在", ex);
        } catch (IOException ex) {
            logger.error("文件读取失败", ex);
        } finally {
            //关闭输入流
            try {
                document.close();
            } catch (IOException e) {
                logger.error("documnet关闭失败", e);
            }
        }
        return info;
    }

    /**
     * 提取pdf文本
     **/
    public static String extractTXT(String pdfFilename) {
        String result = "";
        PDDocument document = null;
        try {
            //获取PDDocument文档对象
            document = PDDocument.load(new File(pdfFilename));
            //获取一个PDFTextStripper文本剥离对象
            PDFTextStripper stripper = new PDFTextStripper();
            //获取文本内容
            result = stripper.getText(document);
        } catch (FileNotFoundException ex) {
            logger.error("文件不存在", ex);
        } catch (IOException ex) {
            logger.error("文件读取失败", ex);
        } finally {
            //关闭输入流
            try {
                document.close();
            } catch (IOException e) {
                logger.error("documnet关闭失败", e);
            }
        }
        return result;
    }

    /**
     * 提取部分页面文本
     *
     * @param pdfFilename pdf文档路径
     * @param startPage   开始页数
     * @param endPage     结束页数
     */
    public static String extractTXT(String pdfFilename, int startPage, int endPage) {
        String result = "";
        PDDocument document = null;
        try {
            //获取PDDocument文档对象
            document = PDDocument.load(new File(pdfFilename));
            //获取一个PDFTextStripper文本剥离对象
            PDFTextStripper stripper = new PDFTextStripper();
            // 设置起始页
            stripper.setStartPage(startPage);
            // 设置结束页
            stripper.setEndPage(endPage);
            //获取文本内容
            result = stripper.getText(document);
        } catch (FileNotFoundException ex) {
            logger.error("文件不存在", ex);
        } catch (IOException ex) {
            logger.error("文件读取失败", ex);
        } finally {
            //关闭输入流
            try {
                document.close();
            } catch (IOException e) {
                logger.error("documnet关闭失败", e);
            }
        }
        return result;
    }

    /**
     * 提取图片
     *
     * @param pdfFilename PDF文档路径
     * @param pageCounter 页码
     */
    public static BufferedImage extractImage(String pdfFilename, int pageCounter) {
        BufferedImage result = null;
        PDDocument document = null;
        try {
            document = PDDocument.load(new File(pdfFilename));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            result = pdfRenderer.renderImageWithDPI(pageCounter, 200, ImageType.RGB);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        String pdfFilePath = "D:\\BaiduYunDownload\\remitOutBank2013-05-16.pdf";
        String picFilePath = "c:\\pic.jpg";
        BufferedImage bufferedImage = PdfConvertTool.extractImage(pdfFilePath, 0);
        ImageIO.write(bufferedImage, "JPG", new File(picFilePath));
    }
}
