package org.rsmod.game.model

import io.netty.channel.Channel
import org.rsmod.protocol.game.packet.DownstreamPacket

public class DownstreamList(
    private val packets: MutableList<DownstreamPacket> = mutableListOf()
) : MutableList<DownstreamPacket> by packets {

    public fun flush(channel: Channel) {
        forEach { channel.write(it) }
        clear()
        channel.flush()
    }
}
