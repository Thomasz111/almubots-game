package com.mygdx.game.physics

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2

interface CirclePhysics {

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

    fun getIncidentalSpeed(hitBox1: Circle, speed1: Vector2, hitBox2: Circle, speed2: Vector2): Vector2
}