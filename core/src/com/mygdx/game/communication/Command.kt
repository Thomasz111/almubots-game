package com.mygdx.game.communication

data class Command(val botNo: Int, val dx: Int, val dy: Int, val rotation: Int, val shoot: Boolean)

class CleanCommand(dirtyCmd: Command) {
    val botNo = dirtyCmd.botNo
    val dx = when {
        dirtyCmd.dx > 0 -> 1
        dirtyCmd.dx == 0 -> 0
        else -> -1
    }
    val dy = when {
        dirtyCmd.dy > 0 -> 1
        dirtyCmd.dy == 0 -> 0
        else -> -1
    }
    val rotation = when {
        dirtyCmd.rotation > 0 -> 1
        dirtyCmd.rotation == 0 -> 0
        else -> -1
    }
    val shoot = dirtyCmd.shoot
}
