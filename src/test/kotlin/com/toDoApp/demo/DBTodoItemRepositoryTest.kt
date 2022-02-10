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
        val sql1 = "DELETE FROM todo"
        val sql2 = "alter table todo alter column id restart with 1"
        jdbcTemplate.update(sql1)
        jdbcTemplate.update(sql2)
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

    @Test
    fun `deleteTodoItems will delete all done items`() {
        var sql = "insert into todo(name, done) values(?,?)"
        jdbcTemplate.update(sql, "shopping", true)
        jdbcTemplate.update(sql, "wake up 7 am", false)

        repo.deleteDoneItems()

        sql = "select * from todo"
        val dBTodoItemsList: List<TodoItem> = jdbcTemplate.query(sql, rowMapper)
        assertThat(dBTodoItemsList[0].name, equalTo("wake up 7 am"))
        assertThat(dBTodoItemsList.size, equalTo(1))

    }

    @Test
    fun `updateTodoItem will update todo item`() {
        var sql = "insert into todo(name, done) values(?,?)"
        jdbcTemplate.update(sql,  "shopping", false)

        repo.updateTodoItem(1, "running", true)

        sql = "select * from todo"
        val dBTodoItemsList: List<TodoItem> = jdbcTemplate.query(sql, rowMapper)
        assertThat(dBTodoItemsList[0].name, equalTo("running"))
        assertThat(dBTodoItemsList[0].done, equalTo(true))
    }

    @Test
    fun `getTodoItemById will get todo item selected by id`() {
        val sql = "insert into todo(name, done) values(?,?)"
        jdbcTemplate.update(sql, "shopping", true)
        jdbcTemplate.update(sql, "cycling", false)


        val result = repo.getTodoItemById(2)

        assertThat(result, equalTo(listOf(TodoItem("cycling", false))))
    }

}