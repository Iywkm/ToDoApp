package com.toDoApp.demo

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper

@SpringBootTest
class DBTodoItemRepositoryTest {


    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    private lateinit var repo: DBTodoItemRepository
    private val rowMapper = RowMapper<TodoItem> { rs, _ ->
        TodoItem(
            rs.getString("name"),
            rs.getBoolean("done")
        )
    }

    @BeforeEach
    fun setup() {
        repo = DBTodoItemRepository(jdbcTemplate)
        val sql = "DELETE FROM todo"
        jdbcTemplate.update(sql)
    }

    @Test
    fun `saveTodoItem will save new todo item`() {
        repo.saveTodoItem(TodoItem("running"))
        repo.saveTodoItem(TodoItem("go to konbini"))

        val sql = "select * from todo"
        val dBTodoItemsList: List<TodoItem> = jdbcTemplate.query(sql, rowMapper)

        assertThat(dBTodoItemsList, equalTo(
                listOf(TodoItem("running"), TodoItem("go to konbini"))
        ))
    }

    @Test
    fun `getTodoItem will get todo items`() {
        val sql = "insert into todo(name, done) values(?,?)"
        jdbcTemplate.update(sql, "shopping", true)

        val result = repo.getTodoItems()
        assertThat(result, equalTo(listOf(TodoItem("shopping", true))))
    }

    @Test
    fun `getDoneItem will get done items`() {
        val sql = "insert into todo(name, done) values(?,?)"
        jdbcTemplate.update(sql, "shopping", true)
        jdbcTemplate.update(sql, "wake up 7 am", false)
        
        val result = repo.getDoneItems()
        assertThat(result, equalTo(listOf(TodoItem("shopping" , true))))
    }

}