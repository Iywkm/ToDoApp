package com.toDoApp.demo

interface TodoItemRepository {
    fun saveTodoItem(todoItem: TodoItem)

    fun getTodoItems(): List<TodoItem>

    fun getDoneItems(): List<TodoItem>

    fun deleteDoneItems()

    fun updateTodoItem(id:Int, name:String, done:Boolean)

    fun getTodoItemById(id:Int): List<TodoItem>
}