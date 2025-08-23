package com.example.kotlinspringcrudwebapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CustomerRequest(
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String
)