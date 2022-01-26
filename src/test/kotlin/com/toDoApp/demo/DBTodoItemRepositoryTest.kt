package com.toDoApp.demo

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test


class DBTodoItemRepositoryTest {

    @Test
    fun `saveTodoItem will save new todo item`() {
        val repo = DBTodoItemRepository()

        repo.saveTodoItem(TodoItem("running"))
        repo.saveTodoItem(TodoItem("go to konbini"))

        assertThat(repo.todoList.toList(), equalTo(
                listOf(TodoItem("running"), TodoItem("go to konbini"))
        ))
    }
}