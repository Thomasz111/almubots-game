package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Circle
import com.mygdx.game.Game
import com.mygdx.game.gameobjects.AlmuBotSimple
import com.mygdx.game.utils.Constants
import ktx.app.KtxScreen

class GameScreen(val game: Game) : KtxScreen {
    // load the image for almuBot, 64x64
    private val botImage = Texture(Gdx.files.internal("assets\\textures\\test.png"))
    // The camera ensures we can render using our target resolution of 800x480
    //    pixels no matter what the screen resolution is.
    private val camera = OrthographicCamera().apply { setToOrtho(false, Constants.screenWidth.toFloat(), Constants.screenHeight.toFloat()) }
    // create a Rectangle to logically represent the almuBot
    // center the almuBot horizontally
    // bottom left almuBot corner is 20px above
    // private val almuBot = Rectangle(800f / 2f - 64f / 2f, 20f, 64f, 64f)
    private val almuBotCircle = AlmuBotSimple(Circle(800f / 2f - 64f / 2f, 20f, 32f))

    override fun render(delta: Float) {
        // generally good practice to update the camera's matrices once per frame
        camera.update()

        // tell the SpriteBatch to render in the coordinate system specified by the camera.
        game.batch.projectionMatrix = camera.combined

        // begin a new batch and draw the almuBot
        game.batch.begin()
        game.font.draw(game.batch, "Y speed: " + almuBotCircle.speed.y, 0f, 480f)
        game.font.draw(game.batch, "X speed: " + almuBotCircle.speed.x, 0f, 440f)
        game.batch.draw(botImage, almuBotCircle.hitBox.x, almuBotCircle.hitBox.y, almuBotCircle.hitBox.radius * 2, almuBotCircle.hitBox.radius * 2)
        game.batch.end()

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

        almuBotCircle.hitBox.x += almuBotCircle.speed.x * delta
        almuBotCircle.hitBox.y += almuBotCircle.speed.y * delta

        // make sure the almuBot stays within the screen bounds
        almuBotCircle.hitBox.x = MathUtils.clamp(almuBotCircle.hitBox.x, 0f, 800f - 64f)
        almuBotCircle.hitBox.y = MathUtils.clamp(almuBotCircle.hitBox.y, 0f, 480f - 64f)
    }

    override fun dispose() {
        botImage.dispose()
    }
}