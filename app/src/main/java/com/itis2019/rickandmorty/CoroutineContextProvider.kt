package com.itis2019.rickandmorty

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvider {
    open val Main: CoroutineContext by lazy { Dispatchers.Main }
    open val Default: CoroutineContext by lazy { Dispatchers.Default }
}

class TestContextProvider : CoroutineContextProvider() {
    override val Main: CoroutineContext = Unconfined
    override val Default: CoroutineContext = Unconfined
}
