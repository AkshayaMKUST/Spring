package org.example.controller;


import org.example.service.NumberService;

public class NumberController {

    @Autowired
    private NumberService numberService;

    @GetMapping("/check/{num}")
    public String check(@PathVariable int num){
        return numberService.checkOddOrEven(num);
    }
}
