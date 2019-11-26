package com.kangjun.util.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.FileOutputStream;

public class QRCodeUtil {
	
	public static void writeToPng(String content,String path) throws Exception {
		writeToPng(content,path,null,null);
	}
	
	public static void writeToPng(String content,String path,Integer width,Integer heigth) throws Exception {
		if(width==null){
			width = 200;
		}
		if(heigth==null){
			heigth = 200;
		}
		content = new String(content.getBytes("UTF-8"),"ISO-8859-1");
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width,heigth);
		FileOutputStream fileOutputStream = new FileOutputStream(path);
		MatrixToImageWriter.writeToStream(matrix, "png", fileOutputStream);
		if(fileOutputStream != null) {
			fileOutputStream.close();
		}
	}
}
