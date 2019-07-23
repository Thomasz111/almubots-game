package com.mygdx.game.physics

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.gameobjects.AlmuBotSimple

class CirclePhysics {

    fun putBotBackToBounds(hitBox: Circle, speed: Vector2, screenWidth: Int, screenHeight: Int) {
        if (hitBox.x - hitBox.radius <= 0) {
            speed.x = -speed.x
        }
        if (hitBox.y - hitBox.radius <= 0) {
            speed.y = -speed.y
        }
        if (hitBox.x + hitBox.radius >=  screenWidth) {
            speed.x = -speed.x
        }
        if (hitBox.y + hitBox.radius >=  screenHeight) {
            speed.y = -speed.y
        }
    }

    fun outOfBounds(hitBox: Circle, screenWidth: Int, screenHeight: Int): Boolean {
        return (hitBox.x - hitBox.radius <= 0) ||
                (hitBox.y - hitBox.radius <= 0) ||
                (hitBox.x + hitBox.radius >=  screenWidth) ||
                (hitBox.y + hitBox.radius >=  screenHeight)
    }

    fun collisionOccured(hitBox1: Circle, hitBox2: Circle): Boolean {
        return hitBox1.overlaps(hitBox2)
    }

    fun setIncidentalSpeed(almuBot1: AlmuBotSimple, almuBot2: AlmuBotSimple) {
        val m = (almuBot1.hitBox.y - almuBot2.hitBox.y) / (almuBot1.hitBox.x - almuBot2.hitBox.x)
        val alpha = Math.atan(m.toDouble())

        val rotatedSpeed1 = Vector2()
        rotatedSpeed1.x = (almuBot1.speed.x * Math.cos(-alpha) - almuBot1.speed.y * Math.sin(-alpha)).toFloat()
        rotatedSpeed1.y = (almuBot1.speed.x * Math.sin(-alpha) + almuBot1.speed.y * Math.cos(-alpha)).toFloat()

        val rotatedSpeed2 = Vector2()
        rotatedSpeed2.x = (almuBot2.speed.x * Math.cos(-alpha) - almuBot2.speed.y * Math.sin(-alpha)).toFloat()
        rotatedSpeed2.y = (almuBot2.speed.x * Math.sin(-alpha) + almuBot2.speed.y * Math.cos(-alpha)).toFloat()

        val newRotatedSpeed1 = Vector2()
        newRotatedSpeed1.x = rotatedSpeed2.x
        newRotatedSpeed1.y = rotatedSpeed1.y

        val newRotatedSpeed2 = Vector2()
        newRotatedSpeed2.x = rotatedSpeed1.x
        newRotatedSpeed2.y = rotatedSpeed2.y

        val newSpeed1 = Vector2()
        newSpeed1.x = (newRotatedSpeed1.x * Math.cos(alpha) - newRotatedSpeed1.y * Math.sin(alpha)).toFloat()
        newSpeed1.y = (newRotatedSpeed1.x * Math.sin(alpha) + newRotatedSpeed1.y * Math.cos(alpha)).toFloat()

        val newSpeed2 = Vector2()
        newSpeed2.x = (newRotatedSpeed2.x * Math.cos(alpha) - newRotatedSpeed2.y * Math.sin(alpha)).toFloat()
        newSpeed2.y = (newRotatedSpeed2.x * Math.sin(alpha) + newRotatedSpeed2.y * Math.cos(alpha)).toFloat()

        almuBot1.speed = newSpeed1
        almuBot2.speed = newSpeed2
    }
}