package com.github.bedrock.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.commons.text.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final HanyuPinyinOutputFormat FORMAT = new HanyuPinyinOutputFormat();

    static {
        FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        FORMAT.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
    }

    private StringUtils() {
    }

    public static boolean isBlank(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isBlank(cs);
    }

    public static boolean isNotBlank(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNotBlank(cs);
    }

    public static Integer toInt(String str) {
        if (isBlank(str)) {
            return null;
        }
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String extract(String source, String regex) {
        if (isBlank(source)) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        return matcher.find() ? source.substring(matcher.start(), matcher.end()) : null;
    }

    public static boolean match(String source, String regex) {
        if (isBlank(source)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        return matcher.matches();
    }

    public static boolean isPhone(String source) {
        return match(source, "^1[3-9][0-9]{9}$");
    }

    public static String unescapeJava(String content) {
        return StringEscapeUtils.unescapeJava(content);
    }

    public static String unescapeHtml4(String content) {
        return StringEscapeUtils.unescapeHtml4(content);
    }

    public static String chineseToPinyin(String chinese) throws Exception {
        if (isBlank(chinese)) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        int length = chinese.length();
        for (int i = 0; i < length; i++) {
            String s = Character.toString(chinese.charAt(i));
            if (s.matches("[\\u4E00-\\u9FA5]+")) {
                res.append(PinyinHelper.toHanyuPinyinStringArray(chinese.charAt(i), FORMAT)[0]);
            } else {
                res.append(s);
            }
        }
        return res.toString();
    }

    public static String leftPad(String source, int length, char padChar) {
        return org.apache.commons.lang3.StringUtils.leftPad(source, length, padChar);
    }

    public static String leftPad(String source, int length, String padStr) {
        return org.apache.commons.lang3.StringUtils.leftPad(source, length, padStr);
    }

    public static String rightPad(String source, int length, char padChar) {
        return org.apache.commons.lang3.StringUtils.rightPad(source, length, padChar);
    }

    public static String rightPad(String source, int length, String padStr) {
        return org.apache.commons.lang3.StringUtils.rightPad(source, length, padStr);
    }
}
