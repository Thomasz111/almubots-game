package com.mygdx.game.gameobjects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.physics.CirclePhysics

class Gun(private val gunImage: Texture){

    var rotation = 0f

    fun draw(batch: Batch, x: Float, y: Float, length: Float, height: Float){
        batch.draw(TextureRegion(gunImage), x, y - height / 2, 0f, height / 2, length, height, 1f, 1f, rotation)
    }

    fun update(){
        if(rotation >= 360)
            rotation %= 360
    }

}