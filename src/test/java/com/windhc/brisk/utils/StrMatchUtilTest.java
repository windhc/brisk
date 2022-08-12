package com.windhc.brisk.utils;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class StrMatchUtilTest {

    @Test
    public void match() {
        StrMatchUtil util = new StrMatchUtil("/aaa/{bbb}/ccc/{ddd}");
        Map<String, String> result = util.match("/aaa/1/ccc/2");
        System.out.println(JSON.toJSONString(result));
    }

}
