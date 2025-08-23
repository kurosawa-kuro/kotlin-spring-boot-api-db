package com.example.kotlinspringcrudwebapi.repository.impl

import com.example.kotlinspringcrudwebapi.model.Customer
import com.example.kotlinspringcrudwebapi.repository.CustomerRepository
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CustomerRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : CustomerRepository {
    
    override fun createCustomer(
        firstName: String,
        lastName: String
    ): Customer {
        val parameters = buildParameters {
            this["first_name"] = firstName
            this["last_name"] = lastName
        }
        
        val generatedId = jdbcTemplate.queryForObject(
            INSERT_CUSTOMER_SQL,
            parameters,
            Long::class.java
        ) ?: throw IllegalStateException("Failed to create customer")
        
        return Customer(
            id = generatedId,
            firstName = firstName,
            lastName = lastName
        )
    }

    override fun findAllCustomers(): List<Customer> = 
        jdbcTemplate.queryForList(
            SELECT_ALL_CUSTOMERS_SQL,
            MapSqlParameterSource()
        ).map { row ->
            mapRowToCustomer(row)
        }

    override fun findCustomerById(id: Int): Customer? {
        val parameters = buildParameters {
            this["id"] = id
        }
        
        return jdbcTemplate.queryForList(
            SELECT_CUSTOMER_BY_ID_SQL,
            parameters
        ).firstOrNull()?.let { row ->
            mapRowToCustomer(row)
        }
    }

    override fun updateCustomer(
        id: Int,
        firstName: String,
        lastName: String
    ): Boolean {
        val parameters = buildParameters {
            this["id"] = id
            this["first_name"] = firstName
            this["last_name"] = lastName
        }
        
        val rowsAffected = jdbcTemplate.update(
            UPDATE_CUSTOMER_SQL,
            parameters
        )
        
        return rowsAffected > 0
    }

    override fun deleteCustomerById(id: Int): Boolean {
        val parameters = buildParameters {
            this["id"] = id
        }
        
        val rowsAffected = jdbcTemplate.update(
            DELETE_CUSTOMER_SQL,
            parameters
        )
        
        return rowsAffected > 0
    }
    
    private fun mapRowToCustomer(row: Map<String, Any>): Customer =
        Customer(
            id = row["id"].toString().toLong(),
            firstName = row["first_name"].toString(),
            lastName = row["last_name"].toString()
        )
    
    private fun buildParameters(
        builder: MutableMap<String, Any?>.() -> Unit
    ): MapSqlParameterSource {
        val params = mutableMapOf<String, Any?>()
        params.builder()
        return MapSqlParameterSource(params)
    }
    
    companion object {
        private const val INSERT_CUSTOMER_SQL = """
            INSERT INTO customer (first_name, last_name) 
            VALUES (:first_name, :last_name) 
            RETURNING id
        """
        
        private const val SELECT_ALL_CUSTOMERS_SQL = """
            SELECT id, first_name, last_name 
            FROM customer
        """
        
        private const val SELECT_CUSTOMER_BY_ID_SQL = """
            SELECT id, first_name, last_name 
            FROM customer 
            WHERE id = :id
        """
        
        private const val UPDATE_CUSTOMER_SQL = """
            UPDATE customer 
            SET first_name = :first_name, last_name = :last_name 
            WHERE id = :id
        """
        
        private const val DELETE_CUSTOMER_SQL = """
            DELETE FROM customer 
            WHERE id = :id
        """
    }
}