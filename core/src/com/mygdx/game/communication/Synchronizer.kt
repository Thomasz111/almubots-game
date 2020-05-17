package com.mygdx.game.communication

import java.util.*

object Synchronizer {
    @Volatile
    var timestamp: Long = Calendar.getInstance().timeInMillis
    @Volatile
    var cmds = listOf<Command>()
    @Volatile
    var gameStatus = GameStatus(listOf(), 0f)
}
