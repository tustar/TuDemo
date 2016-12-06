package com.tustar.demo.util;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;


/**
 * Created by tustar on 15-11-23.
 */
public class SpanUtils {

    private SpanUtils() {

    }

    public static void setSuperScript(Spannable sb, int start, int end) {
        sb.setSpan(new TypefaceSpan("sans-serif-light"), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new AbsoluteSizeSpan(10, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new StyleSpan(Typeface.NORMAL), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new SuperscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static void setSubScript(Spannable sb, int start, int end) {
        sb.setSpan(new TypefaceSpan("sans-serif-light"), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new AbsoluteSizeSpan(10, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new StyleSpan(Typeface.NORMAL), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new SubscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     *
     * @param sb
     * @param start
     * @param end
     * @param textSize
     */
    public static void setSuperScript(Spannable sb, int start, int end, int textSize) {
        sb.setSpan(new TypefaceSpan("sans-serif-light"), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new RelativeSizeSpan(0.5f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new StyleSpan(Typeface.NORMAL), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new SuperscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     *
     * @param sb
     * @param start
     * @param end
     * @param textSize
     */
    public static void setSubScript(Spannable sb, int start, int end, int textSize) {
        sb.setSpan(new TypefaceSpan("sans-serif-light"), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new RelativeSizeSpan(0.5f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new StyleSpan(Typeface.NORMAL), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new SubscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
