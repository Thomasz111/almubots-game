package com.mygdx.game.screen

import com.mygdx.game.GameObj
import com.mygdx.game.communication.GameStatus
import com.mygdx.game.communication.Synchronizer
import com.mygdx.game.managers.BotsManager
import com.mygdx.game.managers.BulletsManager
import java.util.*

class GameScreen(
        private val botsManager: BotsManager,
        private val bulletsManager: BulletsManager
) {

    var deltaMicros = System.nanoTime() / 1000L
    var deltaInit = deltaMicros / 1000L
    var previousTimestamp: Long = System.nanoTime() / 1000L
//    var delta = System.nanoTime() / 1000L
    val calendar = Calendar.getInstance()

    fun render() {
        // generally good practice to update the camera's matrices once per frame
//        camera.update()

        // tell the SpriteBatch to render in the coordinate system specified by the camera.
//        batch.projectionMatrix = camera.combined

        // Respawn


        deltaMicros = System.nanoTime()/1000L - previousTimestamp // DELTA MILLIS JEST W MICROSEKUNDACH
        val delta = deltaMicros/1000000.0f // DELTA MUSI BYC W SEKUNDACH
        previousTimestamp = System.nanoTime()/1000L


        botsManager.manageRespawn(delta)

        val cmds = Synchronizer.cmds

        // begin a new batch and draw the almuBot
//        batch.use {
//        botsManager.printDiagnostics(it, font)
//            botsManager.drawBots(it)
//            bulletsManager.drawBullets(it)
//        }

        // process user input
//        botsManager.processUserInputs()

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

        generateResponse(delta)
        botsManager.clearShoots()
    }

    private fun generateResponse(delta: Float) {
        val botsStatus = botsManager.getStatus()
        Synchronizer.gameStatus = GameStatus(botsStatus, delta)

        Synchronizer.timestamp = Calendar.getInstance().timeInMillis

//        println("Releasing semaphores")
        GameObj.semaphores.forEach { semaphore -> semaphore.release() }
//        Synchronizer.ready.value = true
    }

//    override fun dispose() {
//        botImage.dispose()    // TODO ???
//    }
}