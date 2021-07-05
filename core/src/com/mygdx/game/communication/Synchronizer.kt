package com.mygdx.game.communication

import java.util.*

object Synchronizer {
    @Volatile
    var timestamp: Date = Calendar.getInstance().time
    @Volatile
    var cmds = mutableListOf<Command>()
    @Volatile
    var gameStatus = GameStatus(listOf(), listOf(), reset = false, delta = 0f)
}
