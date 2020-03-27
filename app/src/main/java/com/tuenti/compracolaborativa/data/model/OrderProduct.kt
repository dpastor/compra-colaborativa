package com.tuenti.compracolaborativa.data.model

data class OrderProduct(val name: String, val quantity: Int = 0, val isBought: Boolean = false)