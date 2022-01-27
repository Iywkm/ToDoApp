package com.toDoApp.demo

import org.springframework.stereotype.Service

@Service
class TodoService(val todoRepository: TodoItemRepository): TodoResource {
    override fun createTodoItem(todoItem: TodoItem) {
        todoRepository.saveTodoItem(todoItem)
    }
}

interface TodoResource {
    fun createTodoItem(todoItem: TodoItem)
}
