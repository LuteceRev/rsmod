package org.rsmod.plugins.api.movement

import org.rsmod.game.map.Coordinates
import org.rsmod.game.model.client.MobEntity
import org.rsmod.game.model.mob.Player
import org.rsmod.game.model.mob.list.PlayerList
import org.rsmod.game.model.mob.list.forEachNotNull
import org.rsmod.game.model.route.RouteRequest
import org.rsmod.game.model.route.RouteRequestCoordinates
import org.rsmod.game.model.route.RouteRequestEntity
import org.rsmod.game.pathfinder.Route
import org.rsmod.game.pathfinder.flag.CollisionFlag
import org.rsmod.plugins.api.clearMinimapFlag
import org.rsmod.plugins.api.model.route.RouteRequestGameObject
import org.rsmod.plugins.api.pathfinder.RouteFactory
import org.rsmod.plugins.api.pathfinder.StepFactory
import org.rsmod.plugins.api.setMinimapFlag
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class MovementProcess @Inject constructor(
    private val players: PlayerList,
    private val routeFactory: RouteFactory,
    private val stepFactory: StepFactory
) {

    public fun execute() {
        players.forEachNotNull {
            it.pollRequest()
            it.move()
        }
    }

    private fun Player.pollRequest() {
        val request = routeRequest ?: return
        // TODO: check player can move, etc
        movement.clear()
        appendRequest(request)
        routeRequest = null
    }

    private fun Player.appendRequest(request: RouteRequest) {
        if (request.speed == MoveSpeed.Displace) {
            val displace = request.displaceCoordinates(entity)
            coords = displace
            return
        }
        val route = request.createRoute(entity)
        movement.addAll(route)
        if (route.failed) {
            clearMinimapFlag()
        } else if (route.alternative) {
            val dest = route.last()
            setMinimapFlag(dest.x, dest.z)
        }
    }

    private fun Player.move() {
        var waypoint = movement.peek() ?: return
        var curr = coords
        val steps = movement.speed.steps
        for (i in 0 until steps) {
            if (curr == waypoint) {
                movement.poll()
                waypoint = movement.poll() ?: break
            }
            val step = stepFactory.validated(curr, waypoint, extraFlag = CollisionFlag.BLOCK_PLAYERS)
            if (step == Coordinates.ZERO) break
            curr = step
        }
        // If last step is exact waypoint coords, we remove waypoint from queue.
        if (curr == waypoint) movement.poll()
        coords = curr
    }

    private fun RouteRequest.createRoute(source: MobEntity): Route = when (this) {
        is RouteRequestCoordinates -> routeFactory.create(source, destination)
        is RouteRequestEntity -> routeFactory.create(source, destination)
        is RouteRequestGameObject -> routeFactory.create(source, destination)
        else -> error("Unhandled route request type: $this.")
    }

    // TODO: stepFactory.create(Entity) function
    // TODO: stepFactory.create(GameObject) function
    private fun RouteRequest.displaceCoordinates(source: MobEntity): Coordinates = when (this) {
        is RouteRequestCoordinates -> destination
        is RouteRequestEntity -> destination.coords
        is RouteRequestGameObject -> destination.coords
        else -> error("Unhandled route request type: $this.")
    }
}
