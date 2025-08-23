package com.example.kotlinspringcrudwebapi.controller

import com.example.kotlinspringcrudwebapi.dto.CustomerRequest
import com.example.kotlinspringcrudwebapi.dto.CustomerResponse
import com.example.kotlinspringcrudwebapi.model.Customer
import com.example.kotlinspringcrudwebapi.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/customers")
class CustomerController(private val service: CustomerService) {
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody req: CustomerRequest): Customer {
        validateRequest(req)
        return service.insertCustomer(req.firstName, req.lastName)
    }

    @GetMapping
    fun getAll(): CustomerResponse = CustomerResponse(customers = service.selectCustomer())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): Customer {
        return service.findCustomerById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with ID $id not found")
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody req: CustomerRequest): Customer {
        validateRequest(req)
        return service.updateCustomer(id, req.firstName, req.lastName)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with ID $id not found")
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        if (!service.deleteCustomer(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with ID $id not found")
        }
    }
    
    private fun validateRequest(req: CustomerRequest) {
        if (req.firstName.isBlank()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "First name cannot be blank")
        }
        if (req.lastName.isBlank()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Last name cannot be blank")
        }
    }
}