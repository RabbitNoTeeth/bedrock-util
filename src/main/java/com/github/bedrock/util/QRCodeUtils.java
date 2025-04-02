package com.github.bedrock.util;

import com.github.bedrock.util.entity.QRCodeFormat;
import com.github.bedrock.util.exception.QRCodeException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class QRCodeUtils {

    private static final int DEFAULT_QR_CODE_SIZE = 300;
    private static final QRCodeFormat DEFAULT_QR_CODE_FORMAT = QRCodeFormat.PNG;

    private QRCodeUtils() {
    }

    public static String generateToBase64(String text) throws Exception {
        return generateToBase64(text, DEFAULT_QR_CODE_FORMAT, DEFAULT_QR_CODE_SIZE);
    }

    public static String generateToBase64(String text, QRCodeFormat format) throws Exception {
        return generateToBase64(text, format, DEFAULT_QR_CODE_SIZE);
    }

    public static String generateToBase64(String text, int size) throws Exception {
        return generateToBase64(text, DEFAULT_QR_CODE_FORMAT, size);
    }

    public static String generateToBase64(String text, QRCodeFormat format, int size) throws QRCodeException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, DEFAULT_QR_CODE_SIZE, DEFAULT_QR_CODE_SIZE);
            MatrixToImageWriter.writeToStream(bitMatrix, format.toString(), bos);
            byte[] byteArray = bos.toByteArray();
            return Base64.getEncoder().encodeToString(byteArray);
        } catch (Throwable e) {
            throw new QRCodeException(e);
        }
    }
}
