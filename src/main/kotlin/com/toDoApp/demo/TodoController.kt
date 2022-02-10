package com.toDoApp.demo

import org.springframework.web.bind.annotation.*

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

    @DeleteMapping("/complete")
    fun deleteDoneItems() {
        todoService.deleteDoneItems()
    }

    @PatchMapping("/todo")
    fun updateTodoItem(@RequestParam id: String, name: String, done: String) {
        todoService.updateTodoItem(id.toInt(), name, done.toBoolean())
    }
}

data class TodoItem (val name: String, val done: Boolean = false)