package com.sa.sa1.controller;

import com.sa.sa1.data.HelloDetail;
import com.sa.sa1.service.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HelloWorldWebController {
    private HelloService helloService;

    public HelloWorldWebController(HelloService helloService) {
        this.helloService = helloService;
    }
    @GetMapping("/helloListWeb/{name}")
    public String helloListWeb(@PathVariable String name, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {
        List<HelloDetail> details = new ArrayList<HelloDetail>();
        helloService.helloList(page, size, name).subscribe(details::add);
        model.addAttribute("details", details);
        return "HelloListWeb";
    }

    @GetMapping("/helloListWeb")
    public String helloListAllWeb(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {
        List<HelloDetail> details = new ArrayList<HelloDetail>();
        helloService.helloList(page, size, null).subscribe(details::add);
        model.addAttribute("details", details);
        return "HelloListWeb";
    }
}
