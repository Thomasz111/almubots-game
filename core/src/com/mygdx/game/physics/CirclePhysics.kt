package com.mygdx.game.physics

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import kotlin.math.*

interface CirclePhysics {

    fun putBotBackToBounds(hitBox: Circle, speed: Vector2, screenWidth: Int, screenHeight: Int) {
        if (hitBox.x - hitBox.radius <= 0) {
            speed.x = -speed.x
            hitBox.x = hitBox.radius
        }
        if (hitBox.y - hitBox.radius <= 0) {
            speed.y = -speed.y
            hitBox.y = hitBox.radius
        }
        if (hitBox.x + hitBox.radius >=  screenWidth) {
            speed.x = -speed.x
            hitBox.x = screenWidth.toFloat() - hitBox.radius
        }
        if (hitBox.y + hitBox.radius >=  screenHeight) {
            speed.y = -speed.y
            hitBox.y = screenHeight.toFloat() - hitBox.radius
        }
    }

    fun outOfBounds(hitBox: Circle, screenWidth: Int, screenHeight: Int): Boolean {
        return (hitBox.x - hitBox.radius <= 0) ||
                (hitBox.y - hitBox.radius <= 0) ||
                (hitBox.x + hitBox.radius >=  screenWidth) ||
                (hitBox.y + hitBox.radius >=  screenHeight)
    }

    fun collisionOccurred(hitBox1: Circle, hitBox2: Circle): Boolean {
        return hitBox1.overlaps(hitBox2)
    }

    fun getIncidentalSpeed(hitBox1: Circle, speed1: Vector2, hitBox2: Circle, speed2: Vector2): Vector2

    fun getPositionAfterCollision(hitBox1: Circle, hitBox2: Circle): Vector2 {
        val middlePoint = Vector2((hitBox1.x + hitBox2.x) / 2, (hitBox1.y + hitBox2.y) / 2)

        val angle = atan(abs(hitBox1.y - hitBox2.y) / abs(hitBox1.x - hitBox2.x))

        val properPositionOffset = Vector2(hitBox1.radius * cos(angle), hitBox1.radius * sin(angle))

        var positionAfterCollision = Vector2()
        if(hitBox1.x >= hitBox2.x && hitBox1.y >= hitBox2.y) {
            positionAfterCollision.x = middlePoint.x + properPositionOffset.x
            positionAfterCollision.y = middlePoint.y + properPositionOffset.y
        } else if(hitBox1.x >= hitBox2.x && hitBox1.y <= hitBox2.y) {
            positionAfterCollision.x = middlePoint.x + properPositionOffset.x
            positionAfterCollision.y = middlePoint.y - properPositionOffset.y
        }
        else if(hitBox1.x <= hitBox2.x && hitBox1.y >= hitBox2.y) {
            positionAfterCollision.x = middlePoint.x - properPositionOffset.x
            positionAfterCollision.y = middlePoint.y + properPositionOffset.y
        }
        else {
            positionAfterCollision.x = middlePoint.x - properPositionOffset.x
            positionAfterCollision.y = middlePoint.y - properPositionOffset.y
        }

        return positionAfterCollision
    }
}