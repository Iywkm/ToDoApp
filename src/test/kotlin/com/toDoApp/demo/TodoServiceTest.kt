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

    @Test
    fun `getTodoItems will get todo items`() {
        val stubRepo = StubRepository()
        val service = TodoService(stubRepo)

        val result = service.getTodoItems()

        assertThat(result[0].name, equalTo("shopping"))
        assertThat(result[1].name, equalTo("karaoke"))
    }
}

class StubRepository : TodoItemRepository {
    val getTodoItems_return_value = listOf<TodoItem>(
        TodoItem("shopping"),
        TodoItem("karaoke")
    )

    override fun saveTodoItem(todoItem: TodoItem) {

    }

    override fun getTodoItems(): List<TodoItem> {
        return getTodoItems_return_value
    }

}

class SpyTodoRepository : TodoItemRepository {
    var saveTodoItem_todoItem: TodoItem = TodoItem("")
    var saveTodoItem_was_called = false

    override fun saveTodoItem(todoItem: TodoItem) {
        saveTodoItem_todoItem = todoItem
        saveTodoItem_was_called = true
    }

    override fun getTodoItems(): List<TodoItem> {
        return emptyList()
    }
}
