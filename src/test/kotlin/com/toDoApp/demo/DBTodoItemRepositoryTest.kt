package com.toDoApp.demo

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class DBTodoItemRepositoryTest {
    private lateinit var repo: DBTodoItemRepository

    @BeforeEach
    fun setup() {
        repo = DBTodoItemRepository()
    }

    @Test
    fun `saveTodoItem will save new todo item`() {
        repo.saveTodoItem(TodoItem("running"))
        repo.saveTodoItem(TodoItem("go to konbini"))

        assertThat(dBTodoItemsList, equalTo(
                listOf(TodoItem("running"), TodoItem("go to konbini"))
        ))
    }

    @Test
    fun `getTodoItem will get todo items`() {
        repo.todoList = mutableListOf(TodoItem("shopping"))

        val result = repo.getTodoItems()
        assertThat(result, equalTo(listOf(TodoItem("shopping"))))

    }
}