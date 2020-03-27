package com.tuenti.compracolaborativa.data.apiclients

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuenti.compracolaborativa.data.model.Asker

class Askers {
    val collName = "askers"
    fun get(askerId: String): Task<Asker>? =
        Firebase.firestore.collection(collName)
            .document(askerId)
            .get()
            .continueWith { task ->
                if (task.isSuccessful) task.result!!.toObject(Asker::class.java)?.apply { id = askerId } else null
            }

    fun add(asker: Asker): Task<String> =
        Firebase.firestore.collection(collName)
            .add(asker)
            .continueWith { task ->
                if (task.isSuccessful) task.result?.id else null
            }
}