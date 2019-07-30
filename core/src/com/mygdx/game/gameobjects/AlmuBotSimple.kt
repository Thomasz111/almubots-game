package com.mygdx.game.gameobjects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.physics.CirclePhysics

class AlmuBotSimple(val botId: Int,
                    val hitBox: Circle,
                    private val botImage: Texture,
                    private val physics: CirclePhysics,
                    private val gun: Gun){
    var speed = Vector2(0f, 0f)
    private var previousPosition = Vector2(0f, 0f)
    private var collided = false
    private var newSpeed = Vector2(0f, 0f)
    private var positionAfterCollision = Vector2(0f, 0f)

    fun draw(batch: Batch){
        batch.draw(
            botImage,
            hitBox.x - hitBox.radius,
            hitBox.y - hitBox.radius,
            hitBox.radius * 2,
            hitBox.radius * 2
        )
        gun.draw(batch, this)
    }

    fun outOfBounds(screenWidth: Int, screenHeight: Int): Boolean {
        return physics.outOfBounds(hitBox, screenWidth, screenHeight)
    }

    fun putBotBackToBounds(screenWidth: Int, screenHeight: Int) {
        physics.putBotBackToBounds(hitBox, speed, screenWidth, screenHeight)
    }

    fun collisionOccurredWith(hitBoxOther: Circle): Boolean {
        return physics.collisionOccurred(hitBox, hitBoxOther)
    }

    fun manageCollisionWith(almuBotOther: AlmuBotSimple) {
        collided = true
        newSpeed = physics.getIncidentalSpeed(hitBox, speed, almuBotOther.hitBox, almuBotOther.speed)
        positionAfterCollision = physics.getPositionAfterCollision(hitBox, almuBotOther.hitBox)
    }

    fun manageCollisionWith(bullet: Bullet) {
        if(bullet.botId != botId)
            println(botId.toString() + " hit by " + bullet.botId)
    }

    fun testGunRotation(){
        gun.rotation += 10
    }

    fun testShooting() {
        gun.shoot(this)
    }

    fun update(delta: Float) {
        if (collided) {
            collided = false
            hitBox.x = positionAfterCollision.x
            hitBox.y = positionAfterCollision.y
        }
        previousPosition.x = hitBox.x
        previousPosition.y = hitBox.y
        speed = newSpeed
        hitBox.x += speed.x * delta
        hitBox.y += speed.y * delta

        gun.update()
    }
}