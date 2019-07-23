package com.mygdx.game.gameobjects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2

class AlmuBotSimple(val hitBox: Circle, val botImage: Texture){
    var speed = Vector2(0f, 0f)
    var previousPosition = Vector2(0f, 0f)

    fun draw(batch: Batch){
        batch.draw(botImage, hitBox.x - hitBox.radius, hitBox.y - hitBox.radius, hitBox.radius * 2, hitBox.radius * 2)
    }
}