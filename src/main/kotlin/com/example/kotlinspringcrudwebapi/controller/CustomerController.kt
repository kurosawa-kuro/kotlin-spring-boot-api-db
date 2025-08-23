package com.example.kotlinspringcrudwebapi.controller

import com.example.kotlinspringcrudwebapi.dto.CustomerRequest
import com.example.kotlinspringcrudwebapi.dto.CustomerResponse
import com.example.kotlinspringcrudwebapi.model.Customer
import com.example.kotlinspringcrudwebapi.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val customerService: CustomerService
) {
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(
        @RequestBody request: CustomerRequest
    ): Customer {
        validateCustomerRequest(request)
        return customerService.createCustomer(
            firstName = request.firstName,
            lastName = request.lastName
        )
    }

    @GetMapping
    fun getAllCustomers(): CustomerResponse = 
        CustomerResponse(
            customers = customerService.getAllCustomers()
        )

    @GetMapping("/{id}")
    fun getCustomerById(
        @PathVariable id: Int
    ): Customer = 
        customerService.getCustomerById(id)
            ?: throwCustomerNotFound(id)

    @PutMapping("/{id}")
    fun updateCustomer(
        @PathVariable id: Int,
        @RequestBody request: CustomerRequest
    ): Customer {
        validateCustomerRequest(request)
        return customerService.updateCustomer(
            id = id,
            firstName = request.firstName,
            lastName = request.lastName
        ) ?: throwCustomerNotFound(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(
        @PathVariable id: Int
    ) {
        val deleted = customerService.deleteCustomer(id)
        if (!deleted) {
            throwCustomerNotFound(id)
        }
    }
    
    private fun validateCustomerRequest(request: CustomerRequest) {
        when {
            request.firstName.isBlank() -> 
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "First name cannot be blank"
                )
            request.lastName.isBlank() -> 
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Last name cannot be blank"
                )
        }
    }
    
    private fun throwCustomerNotFound(id: Int): Nothing =
        throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Customer with ID $id not found"
        )
}