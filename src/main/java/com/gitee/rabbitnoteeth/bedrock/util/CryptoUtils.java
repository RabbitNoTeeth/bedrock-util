package com.gitee.rabbitnoteeth.bedrock.util;

import com.gitee.rabbitnoteeth.bedrock.util.exception.CryptoException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.spec.KeySpec;

public class CryptoUtils {

    private CryptoUtils() {
    }

    public static class AESCBCPKCS5Padding {

        private AESCBCPKCS5Padding() {
        }

        public static byte[] decrypt(byte[] content, byte[] key, byte[] iv) throws CryptoException {
            try {
                SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
                return cipher.doFinal(content);
            } catch (Throwable e) {
                throw new CryptoException(e);
            }

        }

    }

    public class MD5 {

        private MD5() {
        }

        private static MessageDigest MD5_DIGEST;

        static {
            try {
                MD5_DIGEST = MessageDigest.getInstance("MD5");
            } catch (Exception e) {
                throw new RuntimeException("md5加密工具创建失败", e);
            }
        }

        public static String encrypt(String source) {
            byte[] byteArray = source.getBytes(StandardCharsets.UTF_8);
            byte[] md5Bytes = MD5_DIGEST.digest(byteArray);
            StringBuilder hexValue = new StringBuilder();
            for (byte md5Byte : md5Bytes) {
                int val = ((int) md5Byte) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        }

        public static byte[] digest(String source) {
            return MD5_DIGEST.digest(source.getBytes(StandardCharsets.UTF_8));
        }

    }

    public static class Pbkdf2Sha256 {

        private Pbkdf2Sha256() {
        }

        public static byte[] hash(String password, String salt, int iterations, int keyLength) throws Exception {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), iterations, keyLength);
            SecretKey secret = keyFactory.generateSecret(keySpec);
            return secret.getEncoded();
        }

    }

}
