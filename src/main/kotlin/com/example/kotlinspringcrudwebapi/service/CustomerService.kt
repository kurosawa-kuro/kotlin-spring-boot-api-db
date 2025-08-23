package com.example.kotlinspringcrudwebapi.service

import com.example.kotlinspringcrudwebapi.model.Customer
import com.example.kotlinspringcrudwebapi.repository.CustomerRepository
import org.springframework.stereotype.Service

interface CustomerService {
    fun insertCustomer(firstName: String, lastName: String): Customer
    fun selectCustomer(): List<Customer>
    fun findCustomerById(id: Int): Customer?
    fun updateCustomer(id: Int, firstName: String, lastName: String): Customer?
    fun deleteCustomer(id: Int): Boolean
}

@Service
class CustomerServiceImpl(private val repo: CustomerRepository) : CustomerService {
    override fun insertCustomer(firstName: String, lastName: String): Customer = repo.add(firstName, lastName)
    
    override fun selectCustomer(): List<Customer> = repo.find()
    
    override fun findCustomerById(id: Int): Customer? = repo.findById(id)
    
    override fun updateCustomer(id: Int, firstName: String, lastName: String): Customer? {
        return if (repo.update(id, firstName, lastName)) {
            Customer(id.toLong(), firstName, lastName)
        } else {
            null
        }
    }
    
    override fun deleteCustomer(id: Int): Boolean = repo.delete(id)
}
