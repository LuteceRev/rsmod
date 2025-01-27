package org.rsmod.game.model.mob.move

import org.rsmod.game.map.Coordinates
import java.util.LinkedList
import java.util.Queue

public class MovementQueue(
    public val waypoints: Queue<Coordinates> = LinkedList(),
    public var speed: MovementSpeed = DefaultMovementSpeed,
    public var noclip: Boolean = false
) : Queue<Coordinates> by waypoints
