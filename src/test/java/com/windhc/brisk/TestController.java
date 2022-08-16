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
    public String a() {
        return "a";
    }

    @Get("/b")
    public String b(@QueryParam(name = "p") String p) {
        return "b" + p;
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

    @Get("/e/{a}/{b}/c")
    public Map<String, String> e(@PathParam(name = "a") String a, @PathParam(name = "b") String b, @QueryParam(name = "p") String p) {
        log.info(a);
        log.info(b);
        log.info(p);
        return Map.of("key1", "value1");
    }
}
