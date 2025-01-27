package org.rsmod.plugins.net.login.downstream

import org.rsmod.protocol.game.packet.ZeroLengthPacketCodec
import javax.inject.Singleton

@Singleton
public class CouldNotCompleteCodec : ZeroLengthPacketCodec<LoginResponse.CouldNotComplete>(
    packet = LoginResponse.CouldNotComplete,
    opcode = 13
)
