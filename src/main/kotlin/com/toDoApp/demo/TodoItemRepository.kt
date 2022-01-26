package com.toDoApp.demo

interface TodoItemRepository {
    fun saveTodoItem(todoItem: TodoItem)
}