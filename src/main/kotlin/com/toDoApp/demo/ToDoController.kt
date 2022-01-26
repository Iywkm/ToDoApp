package com.toDoApp.demo

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ToDoController(val todoService: TodoService) {
    @PostMapping("/todo")
    fun createTodoItem(@RequestBody name: String) {
        todoService.createTodoItem(TodoItem(name))
    }
}

data class TodoItem (val name: String)