package com.tuenti.compracolaborativa.ui.login

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tuenti.compracolaborativa.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

//        address.isFocusable = false
//        address.isClickable = true
//        address.setOnClickListener {
//            // Set the fields to specify which types of place data to
//            // return after the user has made a selection.
//            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)
//            // Start the autocomplete intent.
//            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
//                .setCountry("ES")
//                .build(this)
//            startActivityForResult(intent, AUTOCOMPLETE_RESULT_CODE)
//        }

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            login.isEnabled = loginState.isDataValid

            if (loginState.nameError != null) {
                name.error = getString(loginState.nameError)
            }
            if (loginState.addressError != null) {
                address.error = getString(loginState.addressError)
            }
//            if (loginState.phoneError != null) {
//                phone.error = getString(loginState.phoneError)
//            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        name.afterTextChanged { updateViewModel() }
        address.afterTextChanged { updateViewModel() }
//        phone.afterTextChanged { updateViewModel() }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            loginViewModel.login(name.text.toString(), address.text.toString())
        }
    }

    private fun updateViewModel()  {
        loginViewModel.loginDataChanged(
            name.text.toString(),
            address.text.toString(),
            ""/*phone.text.toString()*/
        )
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == AUTOCOMPLETE_RESULT_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                val place = Autocomplete.getPlaceFromIntent(data!!)
//                Log.i(
//                    "test",
//                    "Place: " + place.name + ", " + place.id + ", " + place.address
//                )
//                val address = place.address
//                // do query with address
//            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) { // TODO: Handle the error.
//                val status = Autocomplete.getStatusFromIntent(data!!)
//                Log.i("test", status.statusMessage ?: "error")
//            } else if (resultCode == Activity.RESULT_CANCELED) { // The user canceled the operation.
//            }
//        }
//    }


    companion object {
        const val AUTOCOMPLETE_RESULT_CODE = 1
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
