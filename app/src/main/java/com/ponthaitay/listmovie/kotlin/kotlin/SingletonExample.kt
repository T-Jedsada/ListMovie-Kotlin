package com.ponthaitay.listmovie.kotlin.kotlin

class SingletonExample {
    object Holder {
        val INSTANCE = SingletonExample()
    }

    companion object {
        val instance: SingletonExample by lazy { Holder.INSTANCE }
    }
}



