package com.github.bedrock.util;

import com.github.bedrock.util.exception.ByteOperationException;

public class ByteUtils {

    private ByteUtils() {
    }

    public static String toBitStr(byte b) {
        return ""
            + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
            + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
            + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
            + (byte) ((b >> 1) & 0x1) + (byte) ((b) & 0x1);
    }

    public static byte fromBitStr(String bitStr) throws ByteOperationException {
        try {
            int length = bitStr.length();
            if (StringUtils.isBlank(bitStr) || length > 8) {
                throw new IllegalArgumentException("invalid bit string");
            }
            int res = 0;
            for (int i = length - 1, move = -1; i >= 0 ; i--, move ++) {
                char bit = bitStr.charAt(i);
                if ('1' == bit) {
                    res = res + (2 << move);
                }
            }
            return (byte) res;
        } catch (Throwable e) {
            throw new ByteOperationException(e);
        }
    }

}
