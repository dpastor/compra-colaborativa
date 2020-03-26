package com.tuenti.compracolaborativa.ui.login

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val nameError: Int? = null,
    val addressError: Int? = null,
    val phoneError: Int? = null,
    val isDataValid: Boolean = false
)
