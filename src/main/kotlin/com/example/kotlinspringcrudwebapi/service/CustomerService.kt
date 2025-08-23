package com.example.kotlinspringcrudwebapi.service

import com.example.kotlinspringcrudwebapi.model.Customer
import com.example.kotlinspringcrudwebapi.repository.CustomerRepository
import org.springframework.stereotype.Service

interface CustomerService {
    fun insertCustomer(firstName: String, lastName: String)
    fun selectCustomer(): List<Customer>
    fun updateCustomer(id: Int, firstName: String, lastName: String)
    fun deleteCustomer(id: Int)
}

@Service
class CustomerServiceImpl(private val repo: CustomerRepository) : CustomerService {
    override fun insertCustomer(firstName: String, lastName: String) = repo.add(firstName, lastName)
    override fun selectCustomer() = repo.find()
    override fun updateCustomer(id: Int, firstName: String, lastName: String) = repo.update(id, firstName, lastName)
    override fun deleteCustomer(id: Int) = repo.delete(id)
}
