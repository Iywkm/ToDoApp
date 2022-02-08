package com.toDoApp.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController(val todoService: TodoResource) {
    @PostMapping("/todo")
    fun createTodoItem(@RequestBody item: TodoItem) {
        todoService.createTodoItem(item)
    }

    @GetMapping("/todo")
    fun getTodoItems(): List<TodoItem> {
        return todoService.getTodoItems()
    }

    @GetMapping("/complete")
    fun getDoneItems(): List<TodoItem> {
        return todoService.getDoneItems()
    }
}

data class TodoItem (val name: String, val done: Boolean = false)