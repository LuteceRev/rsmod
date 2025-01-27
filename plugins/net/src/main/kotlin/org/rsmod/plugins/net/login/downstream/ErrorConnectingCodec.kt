package org.rsmod.plugins.net.login.downstream

import org.rsmod.protocol.game.packet.ZeroLengthPacketCodec
import javax.inject.Singleton

@Singleton
public class ErrorConnectingCodec : ZeroLengthPacketCodec<LoginResponse.ErrorConnecting>(
    packet = LoginResponse.ErrorConnecting,
    opcode = -2
)
