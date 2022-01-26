package com.toDoApp.demo

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class TodoServiceTest {
    @Test
    fun `createTodoItem will create new todo item`() {
        val spyRepo = SpyTodoRepository()
        val service = TodoService(spyRepo)

        service.createTodoItem(TodoItem("test"))

        assertThat(spyRepo.saveTodoItem_todoItem.name, equalTo(
                "test"
        ))
        assertThat(spyRepo.saveTodoItem_was_called, equalTo(
                true
        ))

    }
}

class SpyTodoRepository : TodoItemRepository {
    var saveTodoItem_todoItem: TodoItem = TodoItem("")
    var saveTodoItem_was_called = false

    override fun saveTodoItem(todoItem: TodoItem) {
        saveTodoItem_todoItem = todoItem
        saveTodoItem_was_called = true
    }
}
