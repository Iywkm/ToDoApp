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
        stubRepo.getTodoItems_return_value = listOf<TodoItem>(
            TodoItem("shopping"),
            TodoItem("karaoke")
        )

        val result = service.getTodoItems()

        assertThat(result[0].name, equalTo("shopping"))
        assertThat(result[1].name, equalTo("karaoke"))
    }

    @Test
    fun `doneTodoItems will get done items`() {
        val stubRepo = StubRepository()
        val service = TodoService(stubRepo)
        stubRepo.getDoneItems_return_value = listOf(TodoItem("running", true))

        val result = service.getDoneItems()

        assertThat(result.size, equalTo(1))
        assertThat(result[0].name, equalTo("running"))
        assertThat(result[0].done, equalTo(true))
    }

    @Test
    fun `deleteDoneItems will delete done items`() {
        val spyRepo = SpyTodoRepository()
        val service = TodoService(spyRepo)
        service.deleteDoneItems()
        assertThat(spyRepo.deleteDoneItem_was_called, equalTo(true))
    }

}

class StubRepository : TodoItemRepository {
    var getDoneItems_return_value: List<TodoItem> = emptyList()
    var getTodoItems_return_value: List<TodoItem> = emptyList()
    override fun saveTodoItem(todoItem: TodoItem) {

    }

    override fun getTodoItems(): List<TodoItem> {
        return getTodoItems_return_value
    }

    override fun getDoneItems(): List<TodoItem> {
        return getDoneItems_return_value
    }

    override fun deleteDoneItems() {
    }

}

class SpyTodoRepository : TodoItemRepository {
    var saveTodoItem_todoItem: TodoItem = TodoItem("")
    var saveTodoItem_was_called = false
    var deleteDoneItem_was_called = false

    override fun saveTodoItem(todoItem: TodoItem) {
        saveTodoItem_todoItem = todoItem
        saveTodoItem_was_called = true
    }

    override fun getTodoItems(): List<TodoItem> {
        return emptyList()
    }

    override fun getDoneItems(): List<TodoItem> {
        TODO("Not yet implemented")
    }

    override fun deleteDoneItems() {
        deleteDoneItem_was_called = true
    }
}
