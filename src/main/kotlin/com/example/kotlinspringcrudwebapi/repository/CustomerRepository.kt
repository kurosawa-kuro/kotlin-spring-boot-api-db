package com.example.kotlinspringcrudwebapi.repository

import com.example.kotlinspringcrudwebapi.model.Customer
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

interface CustomerRepository {
    fun add(firstName: String, lastName: String): Customer
    fun find(): List<Customer>
    fun findById(id: Int): Customer?
    fun update(id: Int, firstName: String, lastName: String): Boolean
    fun delete(id: Int): Boolean
}

@Repository
class CustomerRepositoryImpl(private val jdbc: NamedParameterJdbcTemplate) : CustomerRepository {
    
    override fun add(firstName: String, lastName: String): Customer {
        val params = MapSqlParameterSource()
            .addValue("first_name", firstName)
            .addValue("last_name", lastName)
        
        val id = jdbc.queryForObject(
            "INSERT INTO customer (first_name, last_name) VALUES (:first_name, :last_name) RETURNING id",
            params,
            Long::class.java
        )!!
        
        return Customer(id, firstName, lastName)
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

    override fun findById(id: Int): Customer? {
        val result = jdbc.queryForList(
            "SELECT id, first_name, last_name FROM customer WHERE id = :id",
            MapSqlParameterSource().addValue("id", id)
        )
        
        return result.firstOrNull()?.let {
            Customer(
                id = it["id"].toString().toLong(),
                firstName = it["first_name"].toString(),
                lastName = it["last_name"].toString()
            )
        }
    }

    override fun update(id: Int, firstName: String, lastName: String): Boolean {
        val rowsAffected = jdbc.update(
            "UPDATE customer SET first_name = :first_name, last_name = :last_name WHERE id = :id",
            MapSqlParameterSource()
                .addValue("id", id)
                .addValue("first_name", firstName)
                .addValue("last_name", lastName)
        )
        return rowsAffected > 0
    }

    override fun delete(id: Int): Boolean {
        val rowsAffected = jdbc.update(
            "DELETE FROM customer WHERE id = :id",
            MapSqlParameterSource().addValue("id", id)
        )
        return rowsAffected > 0
    }
}