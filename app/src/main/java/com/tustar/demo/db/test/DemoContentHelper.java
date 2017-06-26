package com.tustar.demo.db.test;

import android.content.res.Resources;

import com.tustar.demo.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tustar on 17-3-10.
 */

public class DemoContentHelper {

    private static List<DemoItem> sSpectrumItemsVioletToRed;
    private static List<DemoItem> sSpectrumItemsRedToViolet;

    public static List<DemoItem> getSpectrumItems(Resources res) {
        if (sSpectrumItemsVioletToRed == null) {
            sSpectrumItemsVioletToRed = Arrays.asList(
                    new DemoItem(res.getColor(android.R.color.holo_purple), "PURPLE"),
                    new DemoItem(res.getColor(android.R.color.holo_blue_dark), "BLUE"),
                    new DemoItem(res.getColor(android.R.color.holo_blue_light), "LIGHT BLUE"),
                    new DemoItem(res.getColor(R.color.spectrum_cyan), "CYAN"),
                    new DemoItem(res.getColor(android.R.color.holo_green_dark), "GREEN"),
                    new DemoItem(res.getColor(android.R.color.holo_green_light), "LIGHT GREEN"),
                    new DemoItem(res.getColor(R.color.spectrum_yellow), "YELLOW"),
                    new DemoItem(res.getColor(android.R.color.holo_orange_light), "LIGHT ORANGE"),
                    new DemoItem(res.getColor(android.R.color.holo_orange_dark), "ORANGE"),
                    new DemoItem(res.getColor(android.R.color.holo_red_light), "LIGHT RED"),
                    new DemoItem(res.getColor(android.R.color.holo_red_dark), "RED")
            );
        }
        return sSpectrumItemsVioletToRed;
    }

    public static List<DemoItem> getReverseSpectrumItems(Resources res) {
        if (sSpectrumItemsRedToViolet == null) {
            sSpectrumItemsRedToViolet = Arrays.asList(
                    new DemoItem(res.getColor(android.R.color.holo_red_dark), "RED"),
                    new DemoItem(res.getColor(android.R.color.holo_red_light), "LIGHT RED"),
                    new DemoItem(res.getColor(android.R.color.holo_orange_dark), "ORANGE"),
                    new DemoItem(res.getColor(android.R.color.holo_orange_light), "LIGHT ORANGE"),
                    new DemoItem(res.getColor(R.color.spectrum_yellow), "YELLOW"),
                    new DemoItem(res.getColor(android.R.color.holo_green_light), "LIGHT GREEN"),
                    new DemoItem(res.getColor(android.R.color.holo_green_dark), "GREEN"),
                    new DemoItem(res.getColor(R.color.spectrum_cyan), "CYAN"),
                    new DemoItem(res.getColor(android.R.color.holo_blue_light), "LIGHT BLUE"),
                    new DemoItem(res.getColor(android.R.color.holo_blue_dark), "BLUE"),
                    new DemoItem(res.getColor(android.R.color.holo_purple), "PURPLE")
            );
        }
        return sSpectrumItemsRedToViolet;
    }
}