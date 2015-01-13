package com.example.homework.weather;

import com.example.homework.R;

public class WeatherHelper {
    public static int getIconForCondition(WeatherCondition weatherCondition) {
        switch (weatherCondition.getType()) {
            case CLEAR:
                return R.drawable.icon_w_s;
            case FEW_CLOUDS:
                return R.drawable.icon_w_cs;
            case CLOUDS:
                return R.drawable.icon_w_c;
            case SHOWER:
                return R.drawable.icon_w_cd;
            case RAIN:
                return R.drawable.icon_w_cr;
            case STORM:
                return R.drawable.icon_w_ch;
            default:
                return R.drawable.icon_w_cs;
        }
    }
}