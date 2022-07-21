package com.windhc.brisk;

import com.windhc.brisk.mvc.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author windhc
 */
@Controller("/test")
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Get("/a")
    public String a(@QueryParam(name = "p") String p) {
        return "a" + p;
    }

    @Get("/b")
    public String b() {
        return "b";
    }

    @Get("/c")
    public Map<String, String> c() {
        return Map.of("key1", "value1");
    }

    @Post("/d")
    public Map<String, String> d(@RequestBody TestObj obj) {
        log.info(obj.toString());
        return Map.of("key1", "value1");
    }
}
