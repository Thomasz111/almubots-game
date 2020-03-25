package com.mygdx.game.screen

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.mygdx.game.communication.GameStatus
import com.mygdx.game.communication.Synchronizer
import com.mygdx.game.managers.BotsManager
import com.mygdx.game.managers.BulletsManager
import ktx.app.KtxScreen
import ktx.graphics.use
import java.util.*

class GameScreen(
        private val batch: Batch,
        private val font: BitmapFont,
        private val camera: OrthographicCamera,
        private val botsManager: BotsManager,
        private val bulletsManager: BulletsManager
) : KtxScreen {

    override fun render(delta: Float) {
        // generally good practice to update the camera's matrices once per frame
        camera.update()

        // tell the SpriteBatch to render in the coordinate system specified by the camera.
        batch.projectionMatrix = camera.combined

        // Respawn
        botsManager.manageRespawn(delta)

        val cmds = Synchronizer.cmds

        // begin a new batch and draw the almuBot
        batch.use {
            botsManager.printDiagnostics(it, font)
            botsManager.drawBots(it)
            bulletsManager.drawBullets(it)
        }

        // process user input
        botsManager.processUserInputs()

        // API
        botsManager.processCommands(cmds)
        cmds.clear()

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

        Synchronizer.timestamp = Calendar.getInstance().time
    }

    override fun dispose() {
//        botImage.dispose()    // TODO ???
    }
}