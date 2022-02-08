package com.toDoApp.demo

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class DBTodoItemRepository(val jdbcTemplate: JdbcTemplate): TodoItemRepository {
    private val rowMapper = RowMapper<TodoItem> { rs, _ ->
        TodoItem(
            rs.getString("name"),
            rs.getBoolean("done")
        )
    }

    override fun saveTodoItem(todoItem: TodoItem) {
        val sql = "insert into todo(name, done) values(?,?)"
        jdbcTemplate.update(sql, todoItem.name, todoItem.done)
    }

    override fun getTodoItems(): List<TodoItem> {
        val sql = "select * from todo"
        return jdbcTemplate.query(sql, rowMapper)
    }

    override fun getDoneItems(): List<TodoItem> {
        TODO("Not yet implemented")
    }
}