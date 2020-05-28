package com.mygdx.game.communication

import kotlinx.atomicfu.AtomicBoolean
import kotlinx.atomicfu.AtomicLong
import kotlinx.atomicfu.atomic
import java.util.*

object Synchronizer {
    @Volatile
    var timestamp: Long = Calendar.getInstance().timeInMillis
    @Volatile
    var numOfBotsResponses: AtomicLong = atomic(0L)
    @Volatile
    var cmds = listOf<Command>()
    @Volatile
    var gameStatus = GameStatus(listOf(), 0f)
    @Volatile
    var ready: AtomicBoolean = atomic(false)
}
