package org.tony.chenjy.fulltext_search_service.dev.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestServiceApi {

    @RequestMapping("hello")
    public String hello(@RequestParam(defaultValue = "world") String username) {
        return "hello, " + username;
    }
}
