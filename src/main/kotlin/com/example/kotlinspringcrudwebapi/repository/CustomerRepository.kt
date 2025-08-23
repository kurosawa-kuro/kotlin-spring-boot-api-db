package com.example.kotlinspringcrudwebapi.repository

import com.example.kotlinspringcrudwebapi.model.Customer

interface CustomerRepository {
    fun createCustomer(firstName: String, lastName: String): Customer
    fun findAllCustomers(): List<Customer>
    fun findCustomerById(id: Int): Customer?
    fun updateCustomer(id: Int, firstName: String, lastName: String): Boolean
    fun deleteCustomerById(id: Int): Boolean
}