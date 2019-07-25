package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Circle
import com.mygdx.game.gameobjects.AlmuBotSimple
import com.mygdx.game.physics.CirclePhysics
import com.mygdx.game.utils.Constants
import ktx.app.KtxScreen
import ktx.graphics.use
import java.nio.file.Paths

class GameScreen(
    private val batch: Batch,
    private val font: BitmapFont,
    private val camera: OrthographicCamera,
    private val physics: CirclePhysics,
    private val botsNum: Int
) : KtxScreen {
    private val texturesPath = Paths.get("assets/textures").toAbsolutePath().toString()
    private val botImage = Texture(Gdx.files.internal("$texturesPath/bot.png"))
    private val bots = Array(botsNum) { botNum ->
        val hitBox = Circle(
            Constants.screenWidth.toFloat() / 2f - 64f / 2f,
            (botNum + 1) * 70f,
            32f
        )
        AlmuBotSimple(hitBox, botImage, physics)
    }

    override fun render(delta: Float) {
        // generally good practice to update the camera's matrices once per frame
        camera.update()

        // tell the SpriteBatch to render in the coordinate system specified by the camera.
        batch.projectionMatrix = camera.combined

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

        // wall collisions
        bots.forEach { bot ->
            if (bot.outOfBounds(Constants.screenWidth, Constants.screenHeight)) {
                bot.putBotBackToBounds(Constants.screenWidth, Constants.screenHeight)
            }
        }

        bots.forEach { bot -> bot.update(delta) }
    }

    override fun dispose() {
        botImage.dispose()
    }
}