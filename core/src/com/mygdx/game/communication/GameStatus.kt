package com.mygdx.game.communication

data class GameStatus(val bots: List<BotStatus>) {
    data class BotStatus(val x: Float, val y: Float, val life: Int, val shoot: Boolean)
}