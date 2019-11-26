package com.kangjun.util.qrcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SuppressWarnings("restriction")
public class QrcodeImageUtil {

    /**
     * 临时文件目录
     */
    private static String templatePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    /**
     * 生成图片的临时文件夹
     */
    private static final String QRCODE_PIC_TEMP_DIR = "QRCodeImg";

    /**
     * Description: 打包压缩文件
     *
     * @param srcFiles    需要进行打包的文件集合
     * @param zipFileName 压缩 文件的名称
     * @return File 压缩包文件对象
     * @author cuixubin
     * @since 2018年6月14日: 上午11:54:07
     * Update By cuixubin 2018年6月14日: 上午11:54:07
     */
    public static File zipFiles(File[] srcFiles, String zipFileName) {
        if (null == srcFiles || srcFiles.length == 0) {
            return null;
        }

        String tempDir = templatePath + File.separator + "template" + QRCODE_PIC_TEMP_DIR;
        File fileDir = new File(tempDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        if (null == zipFileName) {
            zipFileName = UUID.randomUUID().toString().replaceAll("-", "");
        }
        zipFileName = tempDir + File.separator + zipFileName + ".zip";
        File zipFile = new File(zipFileName);

        byte[] buf = new byte[1024];

        ZipOutputStream out = null;
        FileInputStream in = null;
        try {
            zipFile.createNewFile();
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < srcFiles.length; i++) {
                in = new FileInputStream(srcFiles[i]);
                out.putNextEntry(new ZipEntry(srcFiles[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return zipFile;
    }

}