package com.windhc.brisk.exception;

import cn.hutool.core.util.StrUtil;

/**
 * @author windhc
 */
public class Assert {

    private Assert() {
    }

    public static void isBlank(String text, String message) {
        if (StrUtil.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }
}
