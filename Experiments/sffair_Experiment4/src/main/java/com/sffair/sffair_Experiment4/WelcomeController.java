/**
 * @author Sullivan Fair (sffair)
 */

package com.sffair.sffair_Experiment4;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController
{
    @GetMapping("/hello")
    public String sayHello()
    {
            return "Hi, welcome to SE 309";
    }
}
