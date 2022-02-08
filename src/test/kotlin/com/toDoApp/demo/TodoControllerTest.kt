package com.toDoApp.demo

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class TodoControllerTest {
    private lateinit var spyService: SpyService
    private lateinit var stubService: StubService
    private lateinit var mockMvc: MockMvc

    private fun setupWithSpyService() {
        spyService = SpyService()
        mockMvc = MockMvcBuilders.standaloneSetup(TodoController(spyService)).build()
    }

    private fun setupWithStubService() {
        stubService = StubService()
        mockMvc = MockMvcBuilders.standaloneSetup(TodoController(stubService)).build()
    }

    @Test
    fun `createTodoItem succeeds and returns status 200`() {
        setupWithSpyService()

        mockMvc.perform(
            post("/todo")
                .content("{\"name\": \"shopping\"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk)

        assertThat(spyService.createTodoItem_todoItem.name, equalTo("shopping"))
        assertThat(spyService.createTodoItem_was_called, equalTo(true))
    }

    @Test
    fun `getTodoItems will return todo item list and status 200`() {
        setupWithStubService()
        stubService.getTodoItems_return_value = listOf<TodoItem>(
            TodoItem("shopping"),
            TodoItem("karaoke")
        )

        mockMvc.perform(
            get("/todo")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name", equalTo("shopping")))
            .andExpect(jsonPath("$[1].name", equalTo("karaoke")))

    }

    @Test
    fun `getDoneItems will return done item list and status 200`() {
        setupWithStubService()
        stubService.getDoneItems_return_value = listOf(TodoItem("running", true))

        mockMvc.perform(
            get("/complete").contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name", equalTo("running")))
            .andExpect(jsonPath("$[0].done", equalTo(true)))
    }



}

class SpyService : TodoResource {
    var createTodoItem_todoItem: TodoItem = TodoItem("")
    var createTodoItem_was_called = false

    override fun createTodoItem(todoItem: TodoItem) {
        createTodoItem_todoItem = todoItem
        createTodoItem_was_called = true
    }

    override fun getTodoItems(): List<TodoItem> {
        return emptyList()
    }

    override fun getDoneItems(): List<TodoItem> {
        return emptyList()
    }
}

class StubService : TodoResource {
    var getTodoItems_return_value: List<TodoItem> = emptyList()
    var getDoneItems_return_value: List<TodoItem> = emptyList()

    override fun createTodoItem(todoItem: TodoItem) {

    }

    override fun getTodoItems(): List<TodoItem> {
        return getTodoItems_return_value
    }

    override fun getDoneItems(): List<TodoItem> {
        return getDoneItems_return_value
    }
}