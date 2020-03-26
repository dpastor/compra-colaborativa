package com.tuenti.compracolaborativa.ui.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.raycoarana.awex.Promise
import com.raycoarana.awex.callbacks.UIDoneCallback
import com.tuenti.compracolaborativa.R
import com.tuenti.compracolaborativa.address.PlacesAddressVerifier
import com.tuenti.compracolaborativa.address.model.Address
import com.tuenti.compracolaborativa.address.model.AddressSuggestion
import com.tuenti.compracolaborativa.core.afterTextChanged
import java.io.Serializable

class AddressActivity : AppCompatActivity() {
    private lateinit var placesAddressVerifier: PlacesAddressVerifier
    private lateinit var addressSearchBar: EditText
    private lateinit var listView: ListView
    private lateinit var resultsAdapter: ArrayAdapter<String>
    private lateinit var formView: View
    private lateinit var formStreet: TextView
    private lateinit var formNumber: TextView
    private lateinit var formStairs: TextView
    private lateinit var formFloor: TextView
    private lateinit var formDoor: TextView
    private lateinit var formCity: TextView
    private lateinit var formPostalCode: TextView
    private lateinit var formSubmit: Button

    private var currentSearch: Promise<List<AddressSuggestion>, Unit>? = null
    private val addressItems = ArrayList<AddressSuggestion>()
    private lateinit var candidateAddress: AddressSuggestion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        resultsAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line)
        placesAddressVerifier = PlacesAddressVerifier(this)

        addressSearchBar = findViewById<EditText>(R.id.address_search_bar).apply {
            afterTextChanged {
                currentSearch?.cancelTask()
                currentSearch =
                    placesAddressVerifier.searchSuggestions(it).done(UIDoneCallback { suggestions ->
                        addressItems.clear()
                        addressItems.addAll(suggestions)
                        resultsAdapter.clear()
                        resultsAdapter.addAll(addressItems.map { item -> item.text })
                    })
            }
        }
        listView = findViewById<ListView>(R.id.suggestions).apply {
            adapter = resultsAdapter
            onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                candidateAddress = addressItems[position]

                addressSearchBar.visibility = View.GONE
                listView.visibility = View.GONE
                formView.visibility = View.VISIBLE
                renderCandidate()
            }
        }

        formView = findViewById(R.id.form)
        formStreet = findViewById(R.id.street)
        formNumber = findViewById(R.id.number)
        formStairs = findViewById(R.id.stairs)
        formFloor = findViewById(R.id.floor)
        formDoor = findViewById(R.id.door)
        formCity = findViewById(R.id.city)
        formPostalCode = findViewById(R.id.postal_code)
        formSubmit = findViewById<Button>(R.id.form_submit).apply {
            setOnClickListener {
                val resultIntent = Intent().putExtra(
                    FIELD_ADDRESS, Address(
                        candidateAddress.id,
                        formStreet.text.toString(),
                        formNumber.text.toString(),
                        formStairs.text.toString(),
                        formFloor.text.toString(),
                        formDoor.text.toString(),
                        formCity.text.toString(),
                        formPostalCode.text.toString(),
                        "",
                        ""
                    )
                )
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun renderCandidate() {
        placesAddressVerifier.fetch(candidateAddress.id).done(UIDoneCallback { address ->
            formStreet.text = address.street
            formNumber.text = address.number
            formFloor.text = address.floor
            formCity.text = address.city
            formPostalCode.text = address.postalCode
        })
    }

    companion object {
        private const val FIELD_ADDRESS = "address"
    }
}
