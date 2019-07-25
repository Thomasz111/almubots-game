package com.mygdx.game.physics

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2

class CirclePhysicsHeavy: CirclePhysics {
    override fun getIncidentalSpeed(hitBox1: Circle, speed1: Vector2, hitBox2: Circle, speed2: Vector2): Vector2 {
        val collisionAngle = speed1.angle(speed2)
        // collision from behind
        return if (collisionAngle <= 90 && collisionAngle >= -90){
            if (speed1.len() > speed2.len()) {
                reflectedSpeed(speed1, hitBox1, hitBox2)
            } else {
                speed1
            }
        } else {
            reflectedSpeed(speed1, hitBox1, hitBox2)
        }
    }

    private fun reflectedSpeed(speed: Vector2, hitBox1: Circle, hitBox2: Circle): Vector2 {
        val m = (hitBox2.x - hitBox1.x) / (hitBox1.y - hitBox2.y)
        val newSpeed = Vector2()
        newSpeed.x = speed.x * ((1 - m * m) / (m * m + 1)) + speed.y * ((2 * m) / (m * m + 1))
        newSpeed.y = speed.x * ((2 * m) / (m * m + 1)) + speed.y * ((m * m - 1) / (m * m + 1))
        return newSpeed
    }
}