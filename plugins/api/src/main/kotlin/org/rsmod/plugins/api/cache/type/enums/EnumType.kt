package org.rsmod.plugins.api.cache.type.enums

import org.rsmod.plugins.api.cache.type.ConfigType
import org.rsmod.plugins.api.cache.type.literal.CacheTypeLiteral

public data class EnumType<K, V>(
    public override val id: Int,
    public val name: String?,
    public val transmit: Boolean,
    public val keyType: CacheTypeLiteral,
    public val valType: CacheTypeLiteral,
    public val default: V?,
    public val properties: MutableMap<K, V>
) : ConfigType, Iterable<Map.Entry<K, V>> {

    public val size: Int get() = properties.size
    public val isEmpty: Boolean get() = properties.isEmpty()
    public val isNotEmpty: Boolean get() = properties.isNotEmpty()

    public fun containsKey(key: K): Boolean = properties.containsKey(key)

    public fun getValue(key: K): V {
        return this[key] ?: throw NoSuchElementException("Key $key is missing in the map.")
    }

    public operator fun get(key: K): V? {
        return properties[key] ?: default
    }

    override fun iterator(): Iterator<Map.Entry<K, V>> {
        return properties.iterator()
    }
}
