package com.mygdx.game.gameobjects

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2

class AlmuBotSimple(val hitBox: Circle){
    var speed = Vector2(0f, 0f)
    var previousPosition = Vector2(0f, 0f)
}