package com.mygdx.game.screen

import com.mygdx.game.communication.Command
import com.mygdx.game.communication.GameStatus
import com.mygdx.game.communication.Synchronizer
import com.mygdx.game.managers.BotsManager
import com.mygdx.game.managers.BulletsManager
import java.util.*

class GameScreen(
        private val botsManager: BotsManager,
        private val bulletsManager: BulletsManager
) {

    fun render(delta: Float) {
        // generally good practice to update the camera's matrices once per frame
//        camera.update()

        // tell the SpriteBatch to render in the coordinate system specified by the camera.
//        batch.projectionMatrix = camera.combined

        // Respawn
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
    }

//    override fun dispose() {
//        botImage.dispose()    // TODO ???
//    }
}