package com.tuenti.compracolaborativa.address

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AddressComponent
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.raycoarana.awex.Awex
import com.raycoarana.awex.Promise
import com.tuenti.compracolaborativa.address.model.Address
import com.tuenti.compracolaborativa.address.model.AddressSuggestion
import com.tuenti.compracolaborativa.core.AwexProvider

class PlacesAddressVerifier constructor(
    context: Context
) {
    private val placesClient: PlacesClient
    private val autoCompleteSessionToken: AutocompleteSessionToken
    private val awex: Awex = AwexProvider.instance

    init {
        Places.initialize(context, "API_KEY")
        placesClient = Places.createClient(context)
        autoCompleteSessionToken = AutocompleteSessionToken.newInstance()
    }

    fun searchSuggestions(searchToken: String): Promise<List<AddressSuggestion>, Unit> {
        val deferred = awex.newAwexPromise<List<AddressSuggestion>, Unit>()

        val request = FindAutocompletePredictionsRequest.builder()
            .setCountry(PLACES_COUNTRY_CODE)
            .setTypeFilter(TypeFilter.ADDRESS)
            .setSessionToken(autoCompleteSessionToken)
            .setQuery(searchToken)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                deferred.resolve(response.autocompletePredictions.map { AddressSuggestion(it.placeId, it.getFullText(null).toString()) })
            }
            .addOnFailureListener { ex -> deferred.reject(ex) }

        return deferred
    }

    fun fetch(addressId: String): Promise<Address, Unit> {
        val deferred = awex.newAwexPromise<Address, Unit>()

        val fetchPlaceRequest = FetchPlaceRequest.builder(addressId, listOf(Place.Field.ADDRESS_COMPONENTS)).build()

        placesClient.fetchPlace(fetchPlaceRequest)
            .addOnSuccessListener { placeResult ->
                val addressComponents = placeResult.place.addressComponents?.asList().orEmpty()

                deferred.resolve(Address(
                    addressId,
                    addressComponents.get("route"),
                    addressComponents.get("street_number"),
                    addressComponents.get("floor"),
                    addressComponents.get("locality"),
                    addressComponents.get("postal_code"),
                    addressComponents.get("administrative_area_level_1"),
                    ""
                ))
            }
            .addOnFailureListener { ex -> deferred.reject(ex) }

        return deferred
    }

    private fun List<AddressComponent>.get(key: String): String = firstOrNull { it.types.contains(key) }?.name ?: ""

    companion object {
        private const val PLACES_COUNTRY_CODE = "es"
    }
}
