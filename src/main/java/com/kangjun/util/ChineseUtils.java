package com.kangjun.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Pattern;

/**
 * 汉字转换位汉语拼音首字母/全拼
 * @author kangjun
 * @date 2023年04月20日 15:45
 */
public class ChineseUtils {
    private static final String CHINESE_PATTERN = "[\u4e00-\u9fa5]";

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变
     *
     * @param chinese 汉字
     * @return 拼音
     */
    public static String convertChineseToFirstSpell(String chinese) {
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : nameChar) {
            if (Pattern.matches(CHINESE_PATTERN, c + "")) {
                try {
                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(
                            c, defaultFormat)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {

                }
            } else {
                pinyinName.append(c);
            }
        }
        return pinyinName.toString();
    }

    /**
     * 汉字转换成汉语拼音全拼，英文字符不变
     *
     * @param chinese 汉字
     * @return 拼音
     * @author you chou
     */
    public static String convertChineseToFullSpell(String chinese) {
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        for (char c : nameChar) {
            if (Pattern.matches(CHINESE_PATTERN, c + "")) {
                try {
                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(
                            c, defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {

                }
            } else {
                pinyinName.append(c);
            }
        }
        return pinyinName.toString();
    }

    public static void main(String[] args) {
        System.out.println(convertChineseToFirstSpell("123qw江苏省徐州市"));
        System.out.println(convertChineseToFullSpell("123qw江苏省徐州市"));
    }
}
