package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demo")
public class Controller {

    @GetMapping
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok().body("ok");
    }
}
