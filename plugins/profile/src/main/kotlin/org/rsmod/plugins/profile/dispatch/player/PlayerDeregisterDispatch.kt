package org.rsmod.plugins.profile.dispatch.player

import com.github.michaelbull.logging.InlineLogger
import org.rsmod.game.events.GameEventBus
import org.rsmod.game.model.client.MobEntity
import org.rsmod.game.model.mob.list.PlayerList
import org.rsmod.plugins.api.model.event.PlayerSession
import org.rsmod.plugins.api.publish
import org.rsmod.plugins.profile.dispatch.transaction.TransactionDispatch
import javax.inject.Inject
import javax.inject.Singleton

private val logger = InlineLogger()

@Singleton
public class PlayerDeregisterDispatch @Inject constructor(
    private val playerList: PlayerList,
    private val eventBus: GameEventBus
) : TransactionDispatch<PlayerDispatchRequest, PlayerDeregisterResponse>() {

    internal fun serve(): Unit = super.serve(TRANSACTIONS_PER_SERVE)

    override fun serve(request: PlayerDispatchRequest): PlayerDeregisterResponse {
        logger.debug { "Serve player deregister response for request $request." }
        val player = request.player
        if (player.index !in playerList.indices) {
            return PlayerDeregisterResponse.NotPreviouslyRegistered
        } else if (playerList[player.index] != player) {
            return PlayerDeregisterResponse.PlayerListElementMismatch
        }
        playerList[player.index] = null
        player.publish(PlayerSession.LogOut(player), eventBus)
        player.publish(PlayerSession.Finalize(player), eventBus)
        player.index = MobEntity.INVALID_INDEX
        return PlayerDeregisterResponse.Success
    }

    private companion object {

        private const val TRANSACTIONS_PER_SERVE = 25
    }
}
