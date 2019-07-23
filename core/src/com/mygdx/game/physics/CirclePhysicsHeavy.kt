package com.mygdx.game.physics

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.gameobjects.AlmuBotSimple

class CirclePhysicsHeavy: CirclePhysics {

    override fun setIncidentalSpeed(almuBot1: AlmuBotSimple, almuBot2: AlmuBotSimple) {
        val collisionAngle = almuBot1.speed.angle(almuBot2.speed)
        // collision from behind
        if (collisionAngle <= 90 && collisionAngle >= -90){
            if (almuBot1.speed.len() > almuBot2.speed.len()) {
                almuBot1.speed = reflectedSpeed(almuBot1.speed, almuBot1.hitBox, almuBot2.hitBox)
            } else {
                almuBot2.speed = reflectedSpeed(almuBot2.speed, almuBot1.hitBox, almuBot2.hitBox)
            }
        } else {
            almuBot1.speed = reflectedSpeed(almuBot1.speed, almuBot1.hitBox, almuBot2.hitBox)

            almuBot2.speed = reflectedSpeed(almuBot2.speed, almuBot1.hitBox, almuBot2.hitBox)
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