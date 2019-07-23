package com.mygdx.game.gameobjects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.physics.CirclePhysics

class AlmuBotSimple(val hitBox: Circle, val botImage: Texture, val physics: CirclePhysics){
    var speed = Vector2(0f, 0f)
    var previousPosition = Vector2(0f, 0f)

    fun draw(batch: Batch){
        batch.draw(botImage, hitBox.x - hitBox.radius, hitBox.y - hitBox.radius, hitBox.radius * 2, hitBox.radius * 2)
    }

    fun outOfBounds(screenWidth: Int, screenHeight: Int): Boolean {
        return physics.outOfBounds(hitBox, screenWidth, screenHeight)
    }

    fun putBotBackToBounds(screenWidth: Int, screenHeight: Int) {
        physics.putBotBackToBounds(hitBox, speed, screenWidth, screenHeight)
        hitBox.x = previousPosition.x
        hitBox.y = previousPosition.y
    }

    fun collisionOccurredWith(hitBoxOther: Circle): Boolean {
        return physics.collisionOccured(hitBox, hitBoxOther)
    }

    fun manageCollisionWith(almuBotOther: AlmuBotSimple) {
        physics.setIncidentalSpeed(this, almuBotOther)
        hitBox.x = previousPosition.x
        hitBox.y = previousPosition.y
        almuBotOther.hitBox.x = almuBotOther.previousPosition.x
        almuBotOther.hitBox.y = almuBotOther.previousPosition.y
    }

    fun update(delta: Float) {
        previousPosition.x = hitBox.x
        previousPosition.y = hitBox.y
        hitBox.x += speed.x * delta
        hitBox.y += speed.y * delta
    }
}