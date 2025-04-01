package com.gitee.rabbitnoteeth.bedrock.util;

public class SystemUtils {

    private SystemUtils() {
    }

    private static String osName() {
        return System.getProperty("os.name");
    }

    private static boolean isWin() {
        return osName().toUpperCase().contains("WIN");
    }

    private static boolean isLinux() {
        return osName().toUpperCase().contains("LINUX");
    }

}
