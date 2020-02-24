package com.pomelo.searchcustomer.utils;

import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {


    public static boolean isEmpty(Object obj) {

        return null == obj || "".equals(obj.toString().trim());
    }

    public static boolean isNotEmpty(Object obj) {

        return !isEmpty(obj);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static String returnNotNull(String name) {
        return StringUtil.isEmpty(name) ? "" : name;
    }

    /**
     * 如果为空返回 暂无
     *
     * @param string
     * @return
     */
    public static String defaultNotNull(String string) {
        return StringUtil.isEmpty(string) ? "暂无" : string;
    }

    /**
     * 去除str最后的逗号
     *
     * @param str
     * @return
     */
    public static String subStringEnd(String str) {
        if (TextUtils.isEmpty(str))
            return "";
        return str.substring(0, str.length() - 1);
    }

    /**
     * 逗号 分割字符串
     *
     * @param str
     * @return
     */
    public static String[] spiltString(String str) {
        if (TextUtils.isEmpty(str))
            return new String[]{};
        return str.split(",");
    }

    /**
     * 截取最后一个等号后的内容
     *
     * @param str
     * @return
     */
    public static String subLastString(String str) {
        if (TextUtils.isEmpty(str))
            return "";
        return str.substring(str.lastIndexOf("=") + 1);
    }

    /**
     * & 分割字符串
     *
     * @param str
     * @return
     */
    public static String[] spiltString1(String str) {
        if (TextUtils.isEmpty(str))
            return new String[]{};
        return str.split("&");
    }

    /**
     * @param str
     * @return
     */
    public static String[] spiltStrings(String str) {
        if (TextUtils.isEmpty(str))
            return new String[]{};
        return str.split("-");
    }

    public static double stringToDouble(String string) {
        double dou = 0d;
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            return dou;
        }
        try {
            dou = Double.parseDouble(string);
        } catch (Exception e) {
            return dou;
        }
        return dou;
    }

    public static int stringToInt(String string) {
        int i = 0;
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            return i;
        }
        try {
            i = Integer.parseInt(string);
        } catch (Exception e) {
            return i;
        }
        return i;
    }

    public static float stringToFloat(String string) {
        float i = 0;
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            return i;
        }
        try {
            i = Float.parseFloat(string);
        } catch (Exception e) {
            return i;
        }
        return i;
    }

    public static long stringToLong(String string) {
        long i = 0l;
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            return i;
        }
        try {
            i = Long.parseLong(string);
        } catch (Exception e) {
            return i;
        }
        return i;
    }

    public static boolean isMobile(String mobiles) {
        String telRegex = "[1][1234567890]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * @param keyWord          关键字
     * @param SubstanceHolonym 字符串
     * @param isItalic         是否是斜体
     * @return
     */
    public static Spannable HighlightText(String keyWord, String SubstanceHolonym, Boolean isItalic) {
        if (SubstanceHolonym == null || SubstanceHolonym.trim().length() <= 0) {
            return new SpannableString("");
        }
        if (keyWord == null || keyWord.trim().length() <= 0) {
            return new SpannableString(SubstanceHolonym);
        }
        Spannable span = null;
        if (keyWord != null || keyWord != "") {
            String str_mark = SubstanceHolonym.replaceAll(keyWord, "<font color='#63B8FF'>" + keyWord + "</font>");
            span = (Spannable) Html.fromHtml(str_mark);
        } else {
            span = new SpannableString(SubstanceHolonym);
        }
        if (isItalic) {
            span.setSpan(new StyleSpan(Typeface.ITALIC), 0, SubstanceHolonym.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return span;
    }

    /**
     * @param keyWord          关键字
     * @param SubstanceHolonym 字符串
     * @return
     */
    public static Spannable HighlightText(String keyWord, String SubstanceHolonym) {
        if (SubstanceHolonym == null || SubstanceHolonym.trim().length() <= 0) {
            return new SpannableString("");
        }
        if (keyWord == null || keyWord.trim().length() <= 0) {
            return new SpannableString(SubstanceHolonym);
        }
        String result = "";
        Spannable span = null;
        if (keyWord != null || keyWord != "") {
            for (int i = 0; i < keyWord.toCharArray().length; i++) {
                char ch = keyWord.toCharArray()[i];
                result = SubstanceHolonym.replaceAll(ch + "", "<font color='#63B8FF'>" + ch + "</font>");
            }
            span = (Spannable) Html.fromHtml(SubstanceHolonym);
        } else {
            span = new SpannableString(SubstanceHolonym);
        }
        return span;
    }

    /**
     * 关键字高亮显示
     *
     * @param target 需要高亮的关键字
     * @param text   需要显示的文字
     * @return spannable 处理完后的结果，记得不要toString()，否则没有效果
     */
    public static SpannableStringBuilder highlight(int color, String target, String text) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        CharacterStyle span = null;

        Pattern p = Pattern.compile(target);
        Matcher m = p.matcher(text);
        while (m.find()) {
            span = new ForegroundColorSpan(color);// 需要重复！
            spannable.setSpan(span, m.start(), m.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    /**
     * 设置红色标记
     *
     * @param tv
     * @param count
     */
    public static void setMark(TextView tv, int count) {
        if (tv == null) return;
        if (count > 0) {
            tv.setVisibility(View.VISIBLE);
            if (count >= 100) {
                tv.setText("99+");
            } else {
                tv.setText(count + "");
            }
        } else {
            tv.setVisibility(View.GONE);
        }
    }


    /**
     * 版本号比较
     * 0代表相等，1代表version1大于version2，-1代表version1小于version2
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }
}
