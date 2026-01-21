package com.mythiqa.mythiqabackend.controller;

import com.mythiqa.mythiqabackend.dto.response.chapter.NewChapterNumDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    public record Greet(String message) {}
    @GetMapping("/home")
    public Greet greet() {
        return new Greet("Hello");
    }
}
