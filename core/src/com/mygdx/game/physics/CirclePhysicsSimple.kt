package com.mygdx.game.physics

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2

class CirclePhysicsSimple: CirclePhysics {
    override fun getIncidentalSpeed(hitBox1: Circle, speed1: Vector2, hitBox2: Circle, speed2: Vector2): Vector2 {
        return speed2
    }
}