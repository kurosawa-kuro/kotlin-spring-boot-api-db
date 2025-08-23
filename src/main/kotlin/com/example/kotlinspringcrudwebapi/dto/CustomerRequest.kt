package com.example.kotlinspringcrudwebapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Customer 作成エンドポイント、Customer 更新エンドポイントのリクエストボディ
 *
 * @property firstName
 * @property lastName
 */
data class CustomerRequest(
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
)