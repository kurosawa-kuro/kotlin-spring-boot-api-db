package com.example.kotlinspringcrudwebapi.service.impl

import com.example.kotlinspringcrudwebapi.model.Customer
import com.example.kotlinspringcrudwebapi.repository.CustomerRepository
import com.example.kotlinspringcrudwebapi.service.CustomerService
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository
) : CustomerService {
    
    override fun createCustomer(
        firstName: String,
        lastName: String
    ): Customer = 
        customerRepository.createCustomer(
            firstName = firstName,
            lastName = lastName
        )
    
    override fun getAllCustomers(): List<Customer> = 
        customerRepository.findAllCustomers()
    
    override fun getCustomerById(id: Int): Customer? = 
        customerRepository.findCustomerById(id)
    
    override fun updateCustomer(
        id: Int,
        firstName: String,
        lastName: String
    ): Customer? =
        customerRepository.updateCustomer(
            id = id,
            firstName = firstName,
            lastName = lastName
        ).takeIf { it }
            ?.let { Customer(id.toLong(), firstName, lastName) }
    
    override fun deleteCustomer(id: Int): Boolean = 
        customerRepository.deleteCustomerById(id)
}