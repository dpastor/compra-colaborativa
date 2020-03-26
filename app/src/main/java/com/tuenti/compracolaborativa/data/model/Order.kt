package com.tuenti.compracolaborativa.data.model

// orderId: d0nrx2BfEdufKcqB3uw3

data class Order (
    val askerId: String = "", // KktfF0W0Ql4Q4qm482XQ
    val buyerId: String? = null, // Z9KAa2RiYkSKpyzREkfr
    val locationId: String = "", // ECoaCtyvFuKQsFh2DTHR
    val products: List<String> = emptyList(), // [ "naranja (rejilla)", "pimiento rojo (2)" ]
    val purchaseId: String? = null // DoCDhIKeBNJHUM9heoLI
)
