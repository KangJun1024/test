package com.kangjun.util.qrcode;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.util.UUID;

/**
 *  二维码
 */
public class QrCode {
    public static void main(String[] args) {
        Boolean kangjun = new QrCode().createQRCode("kangjun", UUID.randomUUID().toString());
        System.out.println(kangjun);
        //=============================================================

    }

    /**
     * Description:创建二维码图片
     *
     * @param content 物理对象id，非必须
     * @return String
     * @author kangjun
     * @since 2019年10月9日: 下午4:16:51
     */
    public  Boolean createQRCode(String content, String key) {
        ApplicationHome h = new ApplicationHome(getClass());
        String rootPath = h.getSource().getParentFile().toString();
        // String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String qrCodeTempDir = rootPath + File.separator + "QRDir";
        String filePath = qrCodeTempDir + File.separator + key + ".png";
        System.out.println(filePath);

        try {
            //生成二维码
            File file = new File(qrCodeTempDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            QRCodeUtil.writeToPng(content, filePath, 400, 400);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
