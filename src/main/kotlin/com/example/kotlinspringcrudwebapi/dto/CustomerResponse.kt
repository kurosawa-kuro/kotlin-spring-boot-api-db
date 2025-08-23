package com.example.kotlinspringcrudwebapi.dto

import com.example.kotlinspringcrudwebapi.model.Customer

data class CustomerResponse(
    val customers: List<Customer>
)