package com.toDoApp.demo

import org.springframework.stereotype.Service

@Service
class TodoService(val todoRepository: TodoItemRepository): TodoResource {
    override fun createTodoItem(todoItem: TodoItem) {
        todoRepository.saveTodoItem(todoItem)
    }

    override fun getTodoItems(): List<TodoItem> {
        return todoRepository.getTodoItems()
    }

    override fun getDoneItems(): List<TodoItem> {
       return todoRepository.getDoneItems()
    }

    override fun deleteDoneItems() {
        todoRepository.deleteDoneItems()
    }
}

interface TodoResource {
    fun createTodoItem(todoItem: TodoItem)
    fun getTodoItems(): List<TodoItem>
    fun getDoneItems(): List<TodoItem>
    fun deleteDoneItems()
}
