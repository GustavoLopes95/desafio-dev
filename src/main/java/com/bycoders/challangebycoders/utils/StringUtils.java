package com.bycoders.challangebycoders.utils;

import org.springframework.stereotype.Component;

@Component
public class StringUtils {
    public static String subStringOrNull(String string, Integer startIndex, Integer endIndex) {
        try {
            return string.substring(startIndex, endIndex);
        } catch (Exception e) {
            return null;
        }
    }
}
