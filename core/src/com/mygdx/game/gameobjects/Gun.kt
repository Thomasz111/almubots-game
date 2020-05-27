package com.mygdx.game.gameobjects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.managers.BulletsManager
import kotlin.math.cos
import kotlin.math.sin

class Gun(private val gunImage: Texture,
          private val bulletsManager: BulletsManager,
          private val length: Float,
          private val height: Float){

    var rotation = 0f

    fun draw(batch: Batch, bot: AlmuBotSimple){
        batch.draw(
                TextureRegion(gunImage),
                bot.hitBox.x,
                bot.hitBox.y - height / 2,
                0f,
                height / 2,
                length,
                height,
                1f,
                1f,
                rotation
        )
    }

    fun shoot(bot: AlmuBotSimple){
        val x = bot.hitBox.x + length * cos(rotation * Math.PI / 180).toFloat()
        val y = bot.hitBox.y + length * sin(rotation * Math.PI / 180).toFloat()
        bulletsManager.spawnBullet(bot, x, y, rotation)
    }

    fun reset() {
        rotation = 0f
    }

    fun update(){
        rotation %= 360
    }

}