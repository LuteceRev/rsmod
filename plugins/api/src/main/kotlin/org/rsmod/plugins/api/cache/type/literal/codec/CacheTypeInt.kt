package org.rsmod.plugins.api.cache.type.literal.codec

public object CacheTypeInt : CacheTypeBaseInt<Int>(Int::class.java) {

    override fun decode(value: Int): Int {
        return value
    }

    override fun encode(value: Int): Int {
        return value
    }
}
