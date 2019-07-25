package com.mygdx.game.physics

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2

class CirclePhysicsMomentum: CirclePhysics {
    override fun getIncidentalSpeed(hitBox1: Circle, speed1: Vector2, hitBox2: Circle, speed2: Vector2): Vector2 {
        val m = (hitBox1.y - hitBox2.y) / (hitBox1.x - hitBox2.x)
        val alpha = Math.atan(m.toDouble())

        val rotatedSpeed1 = Vector2()
        rotatedSpeed1.x = (speed1.x * Math.cos(-alpha) - speed1.y * Math.sin(-alpha)).toFloat()
        rotatedSpeed1.y = (speed1.x * Math.sin(-alpha) + speed1.y * Math.cos(-alpha)).toFloat()

        val rotatedSpeed2 = Vector2()
        rotatedSpeed2.x = (speed2.x * Math.cos(-alpha) - speed2.y * Math.sin(-alpha)).toFloat()
        rotatedSpeed2.y = (speed2.x * Math.sin(-alpha) + speed2.y * Math.cos(-alpha)).toFloat()

        val newRotatedSpeed1 = Vector2()
        newRotatedSpeed1.x = rotatedSpeed2.x
        newRotatedSpeed1.y = rotatedSpeed1.y

        val newSpeed1 = Vector2()
        newSpeed1.x = (newRotatedSpeed1.x * Math.cos(alpha) - newRotatedSpeed1.y * Math.sin(alpha)).toFloat()
        newSpeed1.y = (newRotatedSpeed1.x * Math.sin(alpha) + newRotatedSpeed1.y * Math.cos(alpha)).toFloat()

        return newSpeed1
    }
}