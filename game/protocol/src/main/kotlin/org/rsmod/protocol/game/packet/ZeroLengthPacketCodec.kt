package org.rsmod.protocol.game.packet

import io.netty.buffer.ByteBuf
import org.openrs2.crypto.StreamCipher

public abstract class ZeroLengthPacketCodec<T : Packet>(
    private val packet: T,
    opcode: Int
) : FixedLengthPacketCodec<T>(packet.javaClass, opcode, length = 0) {

    override fun decode(buf: ByteBuf, cipher: StreamCipher): T = packet

    override fun encode(packet: T, buf: ByteBuf, cipher: StreamCipher) { /* empty */ }
}
