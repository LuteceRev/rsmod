package org.rsmod.plugins.api

import com.google.inject.AbstractModule
import org.rsmod.buffer.BufferModule
import org.rsmod.json.JsonModule
import org.rsmod.plugins.api.cache.CacheModule
import org.rsmod.plugins.api.cache.map.xtea.XteaModule
import org.rsmod.plugins.api.core.GameProcessModule
import org.rsmod.plugins.api.info.InfoModule
import org.rsmod.plugins.api.net.PacketModule
import org.rsmod.plugins.api.net.upstream.handler.UpstreamHandlerModule
import org.rsmod.plugins.api.pathfinder.PathFinderModule
import org.rsmod.toml.TomlModule

public object APIModule : AbstractModule() {

    override fun configure() {
        install(BufferModule)
        install(CacheModule)
        install(GameProcessModule)
        install(InfoModule)
        install(JsonModule)
        install(PacketModule)
        install(PathFinderModule)
        install(TomlModule)
        install(UpstreamHandlerModule)
        install(XteaModule)
    }
}
