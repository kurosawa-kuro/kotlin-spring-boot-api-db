package com.example.kotlinspringcrudwebapi.repository

import com.example.kotlinspringcrudwebapi.model.Customer
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

interface CustomerRepository {
    fun add(firstName: String, lastName: String)
    fun find(): List<Customer>
    fun update(id: Int, firstName: String, lastName: String)
    fun delete(id: Int)
}

@Repository
class CustomerRepositoryImpl(private val jdbc: NamedParameterJdbcTemplate) : CustomerRepository {
    
    override fun add(firstName: String, lastName: String) {
        jdbc.update(
            "INSERT INTO customer (first_name, last_name) VALUES (:first_name, :last_name)",
            MapSqlParameterSource()
                .addValue("first_name", firstName)
                .addValue("last_name", lastName)
        )
    }

    override fun find() = jdbc.queryForList(
        "SELECT id, first_name, last_name FROM customer",
        MapSqlParameterSource()
    ).map {
        Customer(
            id = it["id"].toString().toLong(),
            firstName = it["first_name"].toString(),
            lastName = it["last_name"].toString()
        )
    }

    override fun update(id: Int, firstName: String, lastName: String) {
        jdbc.update(
            "UPDATE customer SET first_name = :first_name, last_name = :last_name WHERE id = :id",
            MapSqlParameterSource()
                .addValue("id", id)
                .addValue("first_name", firstName)
                .addValue("last_name", lastName)
        )
    }

    override fun delete(id: Int) {
        jdbc.update(
            "DELETE FROM customer WHERE id = :id",
            MapSqlParameterSource().addValue("id", id)
        )
    }
}