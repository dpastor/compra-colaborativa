package com.tuenti.compracolaborativa.address.model

data class Address(
    val id: String,
    val street: String,
    val number: String,
    val floor: String,
    val city: String,
    val postalCode: String,
    val region: String,
    val additionalInfo: String
)
