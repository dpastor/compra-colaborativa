package com.tuenti.compracolaborativa.core

import com.raycoarana.awex.Awex
import com.raycoarana.awex.android.AndroidLogger
import com.raycoarana.awex.android.AndroidUIThread
import com.raycoarana.awex.policy.LinearWithRealTimePriority

class AwexProvider private constructor() {
    private object HOLDER {
        val INSTANCE = Awex(
            AndroidUIThread(),
            AndroidLogger(),
            LinearWithRealTimePriority(
                MAX_THREADS
            )
        )
    }

    companion object {
        private const val MAX_THREADS = 4

        val instance: Awex by lazy { HOLDER.INSTANCE }
    }
}
