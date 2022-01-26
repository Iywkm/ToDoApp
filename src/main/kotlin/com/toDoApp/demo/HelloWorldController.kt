package com.toDoApp.demo

import org.springframework.web.bind.annotation.*

@RestController
class HelloWorldController {
    @GetMapping("/")
    fun helloWorld(): String {
        return "Hello World"
    }

}