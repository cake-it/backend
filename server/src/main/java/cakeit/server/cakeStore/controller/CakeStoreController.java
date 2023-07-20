package cakeit.server.cakeStore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cake-store")
public class CakeStoreController {

    @GetMapping("/")
    public String test() {
        return "hello";
    }
}
