package com.kangjun.util;


import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.openslide.AssociatedImage;
import org.openslide.OpenSlide;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @author mayifan
 * @date 2023/8/3
 * @apiNote
 */

@Slf4j
@Component
public class OpenSlideUtil {

    private static final String osName = System.getProperty("os.name");
    //    @Value("${open-slide.img-type:jpg}")
    private static String imgType = "jpg";
    //    @Value("${open-slide.img-size:256}")
    private static Integer imgSize = 256;

    //    @Resource
//    private MinIoUtils minIoUtils;

    private static boolean OPENSLIDE_UNAVAILABLE = false;

//    private static final  WIN_LIBRARIES =

    static {
        try {
            // Try loading OpenSlide-JNI - hopefully there is a version of OpenSlide on the PATH we should use
            // 确保本系统已经安装了对应版本的openslide文件并设置了环境变量
//            if (osName.startsWith("Windows")) {
//                log.info("已检测到您当前使用的系统为：WIN");
            System.loadLibrary("openslide-jni");
//                log.info("openslide-jni load success");
//            } else {
//                 linux的so包可以去相关网站下载或者按官网操作打包，目前作者还未用到
//                log.info("已检测到您当前使用的系统为：Linux");
            System.loadLibrary("libopenslide-jni");
//                log.info("libopenslide-jni load success");
//            }
        } catch (UnsatisfiedLinkError e) {
            try {
                // If we didn't succeed, try loading dependencies in reverse order
                log.error("Couldn't load OpenSlide directly, attempting to load dependencies first...", e);
//                if (osName.startsWith("Windows")) {
//                    for (String lib : WIN_LIBRARIES) {
//                        System.loadLibrary(lib);
//                    }
//                }
            } catch (UnsatisfiedLinkError ignored) {
            }
        }
        try {
            // Finally try to get the library version
            log.info("OpenSlide version {}", OpenSlide.getLibraryVersion());
        } catch (UnsatisfiedLinkError e) {
            log.error("Could not load OpenSlide native libraries", e);
            log.info("If you want to use OpenSlide, you'll need to get the native libraries (either building from source or with a packager manager)\n" +
                    "and add them to your system PATH, including openslide-jni.");
            OPENSLIDE_UNAVAILABLE = true;
        }
    }

    /**
     * cutImg
     * 切割图片文件
     * 这个用作本地测试
     *
     * @author: chong
     * @param: infile 输入的文件
     * @param: outfile 输出的文件目录
     * @return: String 文件输出地址
     * @description: 获取文件中的附带文件
     * @date: 2022/4/19
     */
    public static List<String> cutImg(File infile, File outfile) {
        System.out.println(imgType);
        System.out.println(imgSize);
        // 自定义异常，可以根据自己的需要判断或者删除（相关类不进行粘贴）
        List<String> resultAddress = Lists.newArrayList();
        String fileName = infile.getName();
        String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf(Constants.DOT));
        OpenSlide os = null;
        try {
            os = new OpenSlide(infile);
            int levelCount = os.getLevelCount();
            System.out.println(levelCount);
            System.out.println(System.getProperty("java.library.path"));
            // 切割
            for (int n = levelCount - 1; n > -1; n--) { //n表示层级，也表示需要创建的文件夹名字
                int nHeight = (int) (os.getLevel0Height() / os.getLevelHeight(n)); //切高的次数
                int nWidth = (int) (os.getLevel0Width() / os.getLevelWidth(n)); //切长的次数
                for (int j = 0; j < nWidth; j++) {  //循环切长
                    for (int i = 0; i < nHeight; i++) {  //循环切高
                        BufferedImage th = os.createThumbnailImage((int) (j * os.getLevelWidth(n)), (int) (i * os.getLevelHeight(n)), os.getLevelWidth(n), os.getLevelHeight(n), imgSize);  //开始切图
                        // 输出文件到对应目录
                        //创建目录
                        String resultName = outfile + File.separator + (n + 9) + File.separator + j + Constants.UNDERSCORE + i + Constants.DOT + imgType;
                        File file = new File(outfile + File.separator + (n + 9));
                        if (!file.exists()) {
                            System.out.println("文件不存在");
                            file.mkdirs();//创建失败会抛出异常throw new IOException("Invalid file path");
                        }
                        ImageIO.write(th, imgType, new File(resultName));
                        System.out.println("保存成功:" + resultName);
                    }
                }
            }
            // 附件获取
            Map<String, AssociatedImage> map = os.getAssociatedImages();
            for (Map.Entry<String, AssociatedImage> entry : map.entrySet()) {
                BufferedImage img = entry.getValue().toBufferedImage();

                BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics g = result.getGraphics();
                g.drawImage(img, 0, 0, null);
                String resultName = outfile + File.separator + nameWithoutExtension + Constants.DASH + entry.getKey() + Constants.DOT + imgType;
                // 输出文件到对应目录
                ImageIO.write(result, imgType, new File(resultName));
                resultAddress.add(resultName);
            }
            return resultAddress;
        } catch (IOException e) {
            log.error("创建OS对象失败");
            e.printStackTrace();
            return null;
        } finally {
            assert os != null;
            os.close();
        }
    }

    public static void main(String[] args) {
        cutImg(new File("D:\\photos\\CMU-1.tiff"), new File("D:\\photos"));
    }
    /**
     * cutFileToMinIo
     * 切割文件存放到minio
     * 这个用作将上传的文件进行切割保存至相关文件服务器上
     * 由于这里是上传至minio的，所以会有bucketName和directory参数，可以自行进行修改，最后进行测试
     * @param inFile
     * @param bucketName
     * @param directory
     * @author: chong
     * @description
     * @date 2022/5/10
     */
//    public MinioOpenSlideVo cutFileToMinIo(MultipartFile inFile, String bucketName, String directory) {
//        // 自定义异常，可以根据自己的需要判断或者删除（相关类不进行粘贴）
//        SysException.throwException(OPENSLIDE_UNAVAILABLE, ResultCodeEnum.FAIL.code(), "OpenSlide is unavailable - will be skipped");
//        SysException.throwException(StringUtils.isBlank(inFile.getOriginalFilename()), ResultCodeEnum.NOT_FOUND.code(), "切割文件失败！文件不存在！--》" + inFile.getName());
//        OpenSlide os = null;
//        File file = null;
//        MinioOpenSlideVo minioOpenSlideVo = new MinioOpenSlideVo();
//        try {
//            // 转换临时文件
//            file = FileUtils.multipartFileToFile(inFile);
//            String fileName = file.getName();
//            String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf(Constants.POINT));
//            os = new OpenSlide(file);
//            int levelCount = os.getLevelCount();
//            // 切割
//            for (int n = levelCount - 1; n > -1; n--) { //n表示层级，也表示需要创建的文件夹名字
//                int nHeight = (int) (os.getLevel0Height() / os.getLevelHeight(n)); //切高的次数
//                int nWidth = (int) (os.getLevel0Width() / os.getLevelWidth(n)); //切长的次数
//                for (int j = 0; j < nWidth; j++) {  //循环切长
//                    for (int i = 0; i < nHeight; i++) {  //循环切高
//                        BufferedImage image = os.createThumbnailImage((int) (j * os.getLevelWidth(n)), (int) (i * os.getLevelHeight(n)), os.getLevelHeight(n), os.getLevelHeight(n), imgSize);  //开始切图
//                        // 文件名 Constants.UNDERSCORE是下划线_
//                        String resultName = nameWithoutExtension + Constants.UNDERSCORE + j + Constants.UNDERSCORE + i + Constants.POINT + imgType;
//                        // 文件存放位置
//                        String resultPath = directory + File.separator + n + File.separator + nameWithoutExtension + Constants.UNDERSCORE + j + Constants.UNDERSCORE + i;
//                        // 输出流
//                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                        ImageIO.write(image, imgType, outputStream);
//                        //转换为MultipartFile
//                        MultipartFile multipartFile = new MockMultipartFile(resultName, resultName, "application/octet-stream", outputStream.toByteArray());
//                        // 上传minio
//                        minIoUtils.upload(multipartFile, bucketName, resultPath);
//                    }
//                }
//            }
//            // 附件获取
//            Set<String> appendixes = new HashSet<>();
//            Map<String, AssociatedImage> map = os.getAssociatedImages();
//            for (Map.Entry<String, AssociatedImage> entry : map.entrySet()) {
//                BufferedImage image = entry.getValue().toBufferedImage();
//
//                BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
//                Graphics g = result.getGraphics();
//                g.drawImage(image, 0, 0, null);
//                // 文件名
//                String resultName = nameWithoutExtension + Constants.LINE + entry.getKey() + Constants.POINT + imgType;
//                // 文件存放位置 Constants.LINE是横线-
//                String resultPath = directory + File.separator + nameWithoutExtension + Constants.LINE + entry.getKey();
//                // 输出流
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                ImageIO.write(image, imgType, outputStream);
//                //转换为MultipartFile
//                MultipartFile multipartFile = new MockMultipartFile(resultName, resultName, "application/octet-stream", outputStream.toByteArray());
//                // 上传minio
//                minIoUtils.upload(multipartFile, bucketName, resultPath);
//                appendixes.add(entry.getKey());
//            }
//            log.info("文件切割成功！--》" + inFile.getOriginalFilename());
//            minioOpenSlideVo.setLevelCount(levelCount);
//            minioOpenSlideVo.setAppendixes(appendixes);
//            return minioOpenSlideVo;
//        } catch (IOException e) {
//            log.error("文件切割失败！" + inFile.getOriginalFilename());
//            e.printStackTrace();
//            return null;
//        } finally {
//            // 删除临时文件
//            FileUtils.deleteTempFile(file);
//            assert os != null;
//            os.close();
//        }
//    }
}


