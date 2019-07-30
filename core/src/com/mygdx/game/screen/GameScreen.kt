package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Circle
import com.mygdx.game.communication.CleanCommand
import com.mygdx.game.communication.GameStatus
import com.mygdx.game.communication.Synchronizer
import com.mygdx.game.gameobjects.AlmuBotSimple
import com.mygdx.game.gameobjects.Gun
import com.mygdx.game.managers.BulletsManager
import com.mygdx.game.physics.CirclePhysics
import com.mygdx.game.utils.Constants
import ktx.app.KtxScreen
import ktx.graphics.use
import java.nio.file.Paths
import java.util.*

class GameScreen(
    private val batch: Batch,
    private val font: BitmapFont,
    private val camera: OrthographicCamera,
    private val bulletsManager: BulletsManager,
    private val physics: CirclePhysics,
    private val botsNum: Int
) : KtxScreen {
    private val texturesPath = Paths.get("assets/textures").toAbsolutePath().toString()
    private val botImage = Texture(Gdx.files.internal("$texturesPath/bot.png"))
    private val gunImage = Texture(Gdx.files.internal("$texturesPath/test.png"))
    private val bots = Array(botsNum) { botNum ->
        val hitBox = Circle(
            Constants.screenWidth.toFloat() / 2f - 64f / 2f,
            (botNum + 1) * 70f,
            32f
        )
        AlmuBotSimple(botNum, hitBox, botImage, physics, Gun(gunImage, bulletsManager,hitBox.radius * 1.5f, hitBox.radius * 0.5f))
    }

    override fun render(delta: Float) {
        // generally good practice to update the camera's matrices once per frame
        camera.update()

        // tell the SpriteBatch to render in the coordinate system specified by the camera.
        batch.projectionMatrix = camera.combined

        val cmds = Synchronizer.cmds

        // begin a new batch and draw the almuBot
        batch.use {
            if (bots.isNotEmpty()) {
                font.draw(it, "bot1 Y speed: " + bots[0].speed.y, 0f, 480f)
                font.draw(it, "bot1 X speed: " + bots[0].speed.x, 0f, 460f)
            }

            if (bots.size >= 2) {
                font.draw(it, "bot2 Y speed: " + bots[1].speed.y, 0f, 440f)
                font.draw(it, "bot2 X speed: " + bots[1].speed.x, 0f, 420f)
            }

            bots.forEach { bot -> bot.draw(it) }

            bulletsManager.drawBullets(it)
        }

        // process user input
        if (bots.isNotEmpty()) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                bots[0].speed.x -= 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                bots[0].speed.x += 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                bots[0].speed.y += 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                bots[0].speed.y -= 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.V)) {
                bots[0].rotateGun(1)
            }
            if (Gdx.input.isKeyPressed(Input.Keys.B)) {
                bots[0].testShooting()
            }
        }
        if (bots.size >= 2) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                bots[1].speed.x -= 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                bots[1].speed.x += 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                bots[1].speed.y += 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                bots[1].speed.y -= 10
            }
        }

        // API
        cmds.forEach { dirtyCmd ->
            val cmd = CleanCommand(dirtyCmd)
            val bot = bots[cmd.botNo]
            bot.speed.x += 10 * cmd.dx
            bot.speed.y += 10 * cmd.dy
            if (cmd.shoot) {
                bot.shoot = true
                bot.testShooting()
            }
            bot.rotateGun(cmd.rotation)
        }
        cmds.clear()

        // check for collisions with other bots
        for (bot1 in bots) {
            for (bot2 in bots) {
                if (bot1 != bot2) {
                    if (bot1.collisionOccurredWith(bot2.hitBox)) {
                        bot1.manageCollisionWith(bot2)
                    }
                }
            }
        }
        bulletsManager.manageCollisionsWithBots(com.badlogic.gdx.utils.Array(bots))

        // wall collisions
        bots.forEach { bot ->
            if (bot.outOfBounds(Constants.screenWidth, Constants.screenHeight)) {
                bot.putBotBackToBounds(Constants.screenWidth, Constants.screenHeight)
            }
        }
        bulletsManager.manageCollisionsWithWalls()

        bots.forEach { bot -> bot.update(delta) }
        bulletsManager.updateBullets(delta)

        generateResponse()
        bots.forEach { it.shoot = false }
    }

    private fun generateResponse() {
        val botsStatus = bots.map { bot ->
            GameStatus.BotStatus(bot.hitBox.x, bot.hitBox.y, bot.shoot)
        }
        Synchronizer.gameStatus = GameStatus(botsStatus)

        Synchronizer.timestamp = Calendar.getInstance().time
    }

    override fun dispose() {
        botImage.dispose()
    }
}