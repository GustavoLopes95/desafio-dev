package com.bycoders.challangebycoders.utils;

import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@Component
public class DoubleUtils {

    public static String formatDoubleToPattern(Double value) {
        DecimalFormat df = new DecimalFormat("###.00");
        df.setRoundingMode(RoundingMode.UNNECESSARY);
        return df.format(value);
    }
}
