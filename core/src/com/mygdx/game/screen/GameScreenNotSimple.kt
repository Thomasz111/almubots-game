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

class GameScreenNotSimple(private val batch: Batch,
                          private val font: BitmapFont,
                          private val camera: OrthographicCamera) : KtxScreen {
    private val path = Paths.get("assets/textures").toAbsolutePath().toString()
    private val botImage = Texture(Gdx.files.internal("$path/bot.png"))
    private val almuBotCircle = AlmuBotSimple(Circle(Constants.screenWidth.toFloat() / 2f - 64f / 2f, 40f, 32f), botImage, CirclePhysics())
    private val almuBotDummy = AlmuBotSimple(Circle(Constants.screenWidth.toFloat() / 2f - 64f / 2f, 200f, 32f), botImage, CirclePhysics())

    override fun render(delta: Float) {
        // generally good practice to update the camera's matrices once per frame
        camera.update()

        // tell the SpriteBatch to render in the coordinate system specified by the camera.
        batch.projectionMatrix = camera.combined

        // begin a new batch and draw the almuBot
        batch.use {
            font.draw(it, "player Y speed: " + almuBotCircle.speed.y, 0f, 480f)
            font.draw(it, "player X speed: " + almuBotCircle.speed.x, 0f, 460f)

            font.draw(it, "dummy Y speed: " + almuBotDummy.speed.y, 0f, 440f)
            font.draw(it, "dummy X speed: " + almuBotDummy.speed.x, 0f, 420f)

            almuBotCircle.draw(it)
            almuBotDummy.draw(it)
        }

        // process user input
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            almuBotCircle.speed.x -= 10
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            almuBotCircle.speed.x += 10
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            almuBotCircle.speed.y += 10
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            almuBotCircle.speed.y -= 10
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            almuBotDummy.speed.x -= 10
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            almuBotDummy.speed.x += 10
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            almuBotDummy.speed.y += 10
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            almuBotDummy.speed.y -= 10
        }

        // check for collisions with other bots
        if (almuBotCircle.collisionOccurredWith(almuBotDummy.hitBox)) {
            almuBotCircle.manageCollisionWith(almuBotDummy)
        }

        // check if bot is in bounds
        if (almuBotCircle.outOfBounds(Constants.screenWidth, Constants.screenHeight)) {
            almuBotCircle.putBotBackToBounds(Constants.screenWidth, Constants.screenHeight)
        }

        // check if bot is in bounds
        if (almuBotDummy.outOfBounds(Constants.screenWidth, Constants.screenHeight)) {
            almuBotDummy.putBotBackToBounds(Constants.screenWidth, Constants.screenHeight)
        }

        almuBotCircle.update(delta)

        almuBotDummy.update(delta)
    }

    override fun dispose() {
        botImage.dispose()
    }
}