package com.toDoApp.demo

import org.springframework.web.bind.annotation.*

@RestController
class TodoController(val todoService: TodoResource) {
    @PostMapping("/todo")
    fun createTodoItem(@RequestBody item: TodoItem) {
        todoService.createTodoItem(item)
    }

    @GetMapping("/todo")
    fun getTodoItems(@RequestParam id: String?): List<TodoItem> {
        if (id == null) {
            return todoService.getTodoItems()
        } else {
            return todoService.getTodoItemById(id.toInt())
        }

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