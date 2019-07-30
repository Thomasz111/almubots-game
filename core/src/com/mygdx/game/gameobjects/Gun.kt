package com.mygdx.game.gameobjects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.managers.BulletsManager

class Gun(private val gunImage: Texture, private val bulletsManager: BulletsManager, private val length: Float, private val height: Float){

    var rotation = 0f

    fun draw(batch: Batch, x: Float, y: Float){
        batch.draw(TextureRegion(gunImage), x, y - height / 2, 0f, height / 2, length, height, 1f, 1f, rotation)
    }

    fun shoot(x: Float, y: Float){

    }

    fun update(){
        if(rotation >= 360)
            rotation %= 360
    }

}