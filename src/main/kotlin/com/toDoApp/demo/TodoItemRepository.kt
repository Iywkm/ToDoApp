package com.toDoApp.demo

interface TodoItemRepository {
    fun saveTodoItem(todoItem: TodoItem)

    fun getTodoItems(): List<TodoItem>

    fun getDoneItems(): List<TodoItem>

    fun deleteDoneItems()
}