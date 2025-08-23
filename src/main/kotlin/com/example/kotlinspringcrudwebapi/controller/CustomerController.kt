package com.example.kotlinspringcrudwebapi.controller

import com.example.kotlinspringcrudwebapi.dto.CustomerRequest
import com.example.kotlinspringcrudwebapi.dto.CustomerResponse
import com.example.kotlinspringcrudwebapi.service.CustomerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(private val service: CustomerService) {
    @PostMapping
    fun create(@RequestBody req: CustomerRequest) = service.insertCustomer(req.firstName, req.lastName)
        .let { mapOf("message" to "success") }

    @GetMapping
    fun getAll() = CustomerResponse(customers = service.selectCustomer())

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody req: CustomerRequest) = 
        service.updateCustomer(id, req.firstName, req.lastName)
            .let { mapOf("message" to "success") }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) = service.deleteCustomer(id)
        .let { mapOf("message" to "success") }
}


