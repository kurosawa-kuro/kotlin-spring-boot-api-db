package com.example.kotlinspringcrudwebapi.controller

import com.example.kotlinspringcrudwebapi.dto.CustomerRequest
import com.example.kotlinspringcrudwebapi.dto.CustomerResponse
import com.example.kotlinspringcrudwebapi.service.CustomerService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * Customer テーブルの CRUD をおこなう Web API のエンドポイントを定義するクラス
 *
 * @property customerService カスタマーサービス
 */
@RestController
class CustomerController(val customerService: CustomerService) {
    /**
     * Customer 作成エンドポイント
     *
     * @param request
     * @return
     */
    @PostMapping("/customers")
    fun insert(@RequestBody request: CustomerRequest): String {
        customerService.insertCustomer(request.firstName, request.lastName)
        return """
            {
                "message": "success"
            }
        """.trimIndent()
    }

    /**
     * Customer 一覧取得エンドポイント
     *
     * @return
     */
    @GetMapping("/customers")
    fun read(): CustomerResponse {
        return CustomerResponse(customers = customerService.selectCustomer())
    }

    /**
     * Customer 更新エンドポイント
     *
     * @param id
     * @param request
     * @return
     */
    @PutMapping("/customers/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody request: CustomerRequest): String {
        customerService.updateCustomer(id, request.firstName, request.lastName)
        return """
            {
                "message": "success"
            }
        """.trimIndent()
    }

    /**
     * Customer 削除エンドポイント
     *
     * @param id
     * @return
     */
    @DeleteMapping("/customers/{id}")
    fun delete(@PathVariable("id") id: Int): String {
        customerService.deleteCustomer(id)
        return """
            {
                "message": "success"
            }
        """.trimIndent()
    }
}


