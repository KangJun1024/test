package com.kangjun.util.qrcode;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class QrCodeController {

    @GetMapping("downloadQrCodeZip")
    public void downloadQrCodeZip(HttpServletResponse response) {

        String fileName = "kangjun";
        ApplicationHome h = new ApplicationHome(getClass());
        String rootPath = h.getSource().getParentFile().toString();
        String qrCodeTempDir = rootPath + File.separator + "QRDir";
        File[] files = new File[3];
        File file = new File(qrCodeTempDir);
        if(file.isDirectory()){
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(qrCodeTempDir + "\\" + filelist[i]);
                files[i] = readfile;
            }
        }

        File zipFile = QrcodeImageUtil.zipFiles(files, fileName);
        download(response, zipFile, fileName + ".zip");

    }

    /**
     * Description: 导出流
     *
     * @param response
     * @param file
     * @param fileName
     * @return void
     * @auther: kangjun
     * @date: 2019/10/9 15:52
     * @version: V1.0
     */
    public void download(HttpServletResponse response, File file, String fileName) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;

        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);

            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("export file finish");
    }

}
