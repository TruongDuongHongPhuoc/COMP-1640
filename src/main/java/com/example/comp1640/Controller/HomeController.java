package com.example.comp1640.Controller;

import com.example.comp1640.model.Customer;
import com.example.comp1640.repository.FalcultyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class HomeController {
    final private List<Customer> customers = List.of(
            Customer.builder().id("001").name("Customer 1").email("c1@gmail.com").build(),
            Customer.builder().id("002").name("Customer 2").email("c2@gmail.com").build()
    );
    FalcultyRepository falcultyRepository;


    @GetMapping("/home")
    public String home(){
        return "HomePage";
    }
        @GetMapping("/layout")
    public String layout(){
        return "/Layout/_Customer";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "Login success!";
    }
}
