package com.toDoApp.demo

import org.springframework.stereotype.Repository

@Repository
class DBTodoItemRepository: TodoItemRepository {
    var todoList: MutableList<TodoItem> = mutableListOf()

    override fun saveTodoItem(todoItem: TodoItem) {
        todoList.add(todoItem)
    }

    override fun getTodoItems(): List<TodoItem> {
        return todoList
    }
}