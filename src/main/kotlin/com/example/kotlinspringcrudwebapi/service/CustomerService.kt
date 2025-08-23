package com.example.kotlinspringcrudwebapi.service

import com.example.kotlinspringcrudwebapi.model.Customer

interface CustomerService {
    fun createCustomer(firstName: String, lastName: String): Customer
    fun getAllCustomers(): List<Customer>
    fun getCustomerById(id: Int): Customer?
    fun updateCustomer(id: Int, firstName: String, lastName: String): Customer?
    fun deleteCustomer(id: Int): Boolean
}