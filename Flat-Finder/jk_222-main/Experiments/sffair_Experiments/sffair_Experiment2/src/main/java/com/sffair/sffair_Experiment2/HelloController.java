package com.sffair.sffair_Experiment2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{
    @GetMapping("/hello")
    public String sayHello()
    {
        return "Hello, Com S 309";
    }
}
