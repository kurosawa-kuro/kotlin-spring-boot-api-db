package com.example.kotlinspringcrudwebapi.dto

import com.example.kotlinspringcrudwebapi.model.Customer

/**
 * Customer 一覧取得エンドポイントのレスポンス
 *
 * @property customers
 */
data class CustomerResponse(
    val customers: List<Customer>,
)