package com.tuenti.compracolaborativa.domain.login

import android.content.Context
import android.content.Context.MODE_PRIVATE

class DoLogin(val context: Context) {
    operator fun invoke(name: String, address: String, addressDetail: String, shopper: Boolean): Boolean {

        UserToken(context).put("")
        return true
    }
}

class UserToken(val context: Context) {
    fun get(): String? = context.getSharedPreferences("shared_prefs", MODE_PRIVATE).getString("TOKEN", null)
    fun put(token: String) {context.getSharedPreferences("shared_prefs", MODE_PRIVATE).edit().putString("TOKEN", token).apply()}
}