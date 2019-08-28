package com.mygdx.game.communication

data class GameStatus(val bots: List<BotStatus>, val delta: Float) {
    data class BotStatus(
        val id: Int,
        val x: Float,
        val y: Float,
        val vx: Float,
        val vy: Float,
        val angle: Float,
        val ammo: Int,
        val life: Int,
        val shoot: Boolean,
        val score: Int
    )
}