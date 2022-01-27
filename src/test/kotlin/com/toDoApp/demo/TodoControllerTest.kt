package com.toDoApp.demo

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class TodoControllerTest {
    val spyService = SpyService()
    val mockMvc = MockMvcBuilders.standaloneSetup(TodoController(spyService)).build()

    @Test
    fun `createTodoItem succeeds and returns status 200`() {
        mockMvc.perform(
                post("/todo")
                        .content("{\"name\": \"shopping\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)

        assertThat(spyService.createTodoItem_todoItem.name, equalTo("shopping"))
        assertThat(spyService.createTodoItem_was_called, equalTo(true))
    }

}

class SpyService: TodoResource {
    var createTodoItem_todoItem: TodoItem = TodoItem("")
    var createTodoItem_was_called = false

    override fun createTodoItem(todoItem: TodoItem) {
        createTodoItem_todoItem = todoItem
        createTodoItem_was_called = true
    }

}