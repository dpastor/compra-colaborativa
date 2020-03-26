package com.tuenti.compracolaborativa.address.model

import java.io.Serializable

data class Address(
    val id: String,
    val street: String,
    val number: String,
    val floor: String,
    val stairs: String,
    val door: String,
    val city: String,
    val postalCode: String,
    val region: String,
    val additionalInfo: String
): Serializable
