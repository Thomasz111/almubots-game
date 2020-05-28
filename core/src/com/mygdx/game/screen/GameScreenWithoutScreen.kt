package com.mygdx.game.screen

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.GameObj
import com.mygdx.game.communication.GameStatus
import com.mygdx.game.communication.Synchronizer
import com.mygdx.game.managers.BotsManager
import com.mygdx.game.managers.BulletsManager
import com.mygdx.game.utils.Constants
import ktx.app.KtxScreen
import ktx.graphics.use
import java.util.*


class GameScreenWithoutScreen(private val botsManager: BotsManager,
                 private val bulletsManager: BulletsManager
) {

    private var gameReset = false
    private var roundNum = 0
    private val delta = 0.0166666666667f

    fun render() {
        gameReset = false
        roundNum += 1

        var deltaMicros = System.nanoTime() / 1000L
        var deltaInit = deltaMicros / 1000L
        var previousTimestamp: Long = System.nanoTime() / 1000L
//    var delta = System.nanoTime() / 1000L
        val calendar = Calendar.getInstance()

        deltaMicros = System.nanoTime() / 1000L - previousTimestamp // DELTA MILLIS JEST W MICROSEKUNDACH
//        val delta = deltaMicros / 1000000.0f // DELTA MUSI BYC W SEKUNDACH
        previousTimestamp = System.nanoTime() / 1000L


        botsManager.manageRespawn(delta)

        val cmds = Synchronizer.cmds

        // API
        botsManager.processCommands(cmds)
        Synchronizer.cmds = listOf()

        // check for collisions with other bots
        botsManager.manageCollisionsWithBots()
        bulletsManager.manageCollisionsWithBots(com.badlogic.gdx.utils.Array(botsManager.bots))

        // wall collisions
        botsManager.manageCollisionsWithWalls()
        bulletsManager.manageCollisionsWithWalls()

        botsManager.updateBots(delta)
        bulletsManager.updateBullets(delta)

        if (roundNum >= MAX_NUM_OF_ROUNDS) {
            botsManager.reset()
            bulletsManager.reset()
            gameReset = true
            roundNum = 0
        }

        generateResponse(gameReset, delta)
        botsManager.clearShoots()
    }

    private fun generateResponse(gameReset: Boolean, delta: Float) {
        val botsStatus = botsManager.getStatus()
        Synchronizer.gameStatus = GameStatus(botsStatus, gameReset, delta)

        Synchronizer.timestamp = Calendar.getInstance().timeInMillis

//        println("Releasing semaphores")
        GameObj.semaphores.forEach { semaphore -> semaphore.release() }
//        Synchronizer.ready.value = true
    }

    fun dispose() {
//        botImage.dispose()    // TODO ???
    }
}