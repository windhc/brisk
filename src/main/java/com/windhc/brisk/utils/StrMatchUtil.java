package com.windhc.brisk.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字符串模式匹配，使用{XXX}作为变量
 *
 * @author windhc
 */
public class StrMatchUtil {

    List<String> patterns;

    /**
     * 构造
     *
     * @param pattern 模式，变量用{XXX}占位
     */
    public StrMatchUtil(String pattern) {
        this.patterns = parse(pattern);
    }

    /**
     * 匹配并提取匹配到的内容
     *
     * @param text 被匹配的文本
     * @return 匹配的map，key为变量名，value为匹配到的值
     */
    public Map<String, String> match(String text) {
        final HashMap<String, String> result = MapUtil.newHashMap(true);
        int from = 0;
        String key = null;
        int to;
        for (String part : patterns) {
            if (StrUtil.isWrap(part, "{", "}")) {
                // 变量
                key = StrUtil.sub(part, 1, part.length() - 1);
            } else {
                to = text.indexOf(part, from);
                if (to < 0) {
                    //普通字符串未匹配到，说明整个模式不能匹配，返回空
                    return MapUtil.empty();
                }
                if (null != key && to > from) {
                    // 变量对应部分有内容
                    result.put(key, text.substring(from, to));
                }
                // 下一个起始点是普通字符串的末尾
                from = to + part.length();
                key = null;
            }
        }

        if (null != key && from < text.length()) {
            // 变量对应部分有内容
            result.put(key, text.substring(from));
        }

        return result;
    }

    /**
     * 解析表达式
     *
     * @param pattern 表达式，使用{XXX}作为变量占位符
     * @return 表达式
     */
    private static List<String> parse(String pattern) {
        List<String> patterns = new ArrayList<>();
        final int length = pattern.length();
        char c = 0;
        boolean inVar = false;
        StringBuilder part = StrUtil.builder();
        for (int i = 0; i < length; i++) {
            c = pattern.charAt(i);
            if (inVar) {
                part.append(c);
                if ('}' == c) {
                    // 变量结束
                    inVar = false;
                    patterns.add(part.toString());
                    part.setLength(0);
                }
            } else if ('{' == c) {
                // 变量开始
                inVar = true;
                final String preText = part.substring(0, part.length());
                if (StrUtil.isNotEmpty(preText)) {
                    patterns.add(preText);
                }
                part.setLength(0);
                part.append(c);
            } else {
                // 普通字符
                part.append(c);
            }
        }

        if (part.length() > 0) {
            patterns.add(part.toString());
        }
        return patterns;
    }
}
