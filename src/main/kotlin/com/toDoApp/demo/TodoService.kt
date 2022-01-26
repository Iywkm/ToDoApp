package com.toDoApp.demo

import org.springframework.stereotype.Service

@Service
class TodoService(val todoRepository: TodoItemRepository) {
    fun createTodoItem(todoItem: TodoItem) {
        todoRepository.saveTodoItem(todoItem)
    }
}
