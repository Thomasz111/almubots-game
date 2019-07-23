package com.mygdx.game.physics

import com.mygdx.game.gameobjects.AlmuBotSimple

class CirclePhysicsSimple: CirclePhysics {

    override fun setIncidentalSpeed(almuBot1: AlmuBotSimple, almuBot2: AlmuBotSimple) {
        val previousSpeed = almuBot1.speed
        almuBot1.speed = almuBot2.speed
        almuBot2.speed = previousSpeed
    }
}