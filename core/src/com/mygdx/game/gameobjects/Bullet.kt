package com.mygdx.game.gameobjects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.physics.CirclePhysics

class Bullet(val bot: AlmuBotSimple,
             val hitBox: Circle,
             private val bulletImage: Texture,
             private val physics: CirclePhysics,
             val speed: Vector2,
             val rotation: Float){
    private var collided = false

    fun draw(batch: Batch){
        batch.draw(
                TextureRegion(bulletImage),
                hitBox.x - hitBox.radius,
                hitBox.y - hitBox.radius,
                hitBox.radius,
                hitBox.radius,
                hitBox.radius * 2,
                hitBox.radius * 2,
                1f,
                1f,
                rotation
        )
    }

    fun outOfBounds(screenWidth: Int, screenHeight: Int): Boolean {
        return physics.outOfBounds(hitBox, screenWidth, screenHeight)
    }

    fun collisionOccurredWith(hitBoxOther: Circle): Boolean {
        return physics.collisionOccurred(hitBox, hitBoxOther)
    }

    fun manageCollisionWith(almuBotOther: AlmuBotSimple) {
        if(almuBotOther.botId != bot.botId)
            collided = true
    }

    fun update(delta: Float) {
        hitBox.x += speed.x * delta
        hitBox.y += speed.y * delta
    }
}