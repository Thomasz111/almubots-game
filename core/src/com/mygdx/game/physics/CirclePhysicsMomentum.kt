package com.mygdx.game.physics

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.gameobjects.AlmuBotSimple

class CirclePhysicsMomentum: CirclePhysics {

    override fun setIncidentalSpeed(almuBot1: AlmuBotSimple, almuBot2: AlmuBotSimple) {
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