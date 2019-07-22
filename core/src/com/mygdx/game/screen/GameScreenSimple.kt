package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Game
import com.mygdx.game.gameobjects.AlmuBotSimple
import com.mygdx.game.utils.Constants
import ktx.app.KtxScreen

class GameScreenSimple(val game: Game) : KtxScreen {
    // load the image for almuBot, 64x64
    private val botImage = Texture(Gdx.files.internal("assets\\textures\\bot.png"))
    // The camera ensures we can render using our target resolution of 800x480
    //    pixels no matter what the screen resolution is.
    private val camera = OrthographicCamera().apply { setToOrtho(false, Constants.screenWidth.toFloat(), Constants.screenHeight.toFloat()) }
    // create a Rectangle to logically represent the almuBot
    // center the almuBot horizontally
    // bottom left almuBot corner is 20px above
    // private val almuBot = Rectangle(800f / 2f - 64f / 2f, 20f, 64f, 64f)
    private val almuBotCircle = AlmuBotSimple(Circle(Constants.screenWidth.toFloat() / 2f - 64f / 2f, 40f, 32f))
    private val almuBotDummy = AlmuBotSimple(Circle(Constants.screenWidth.toFloat() / 2f - 64f / 2f, 200f, 32f))

    override fun render(delta: Float) {
        // generally good practice to update the camera's matrices once per frame
        camera.update()

        // tell the SpriteBatch to render in the coordinate system specified by the camera.
        game.batch.projectionMatrix = camera.combined

        // begin a new batch and draw the almuBot
        game.batch.begin()
        game.font.draw(game.batch, "Y speed: " + almuBotCircle.speed.y, 0f, 480f)
        game.font.draw(game.batch, "X speed: " + almuBotCircle.speed.x, 0f, 460f)

        game.font.draw(game.batch, "dummy Y speed: " + almuBotDummy.speed.y, 0f, 440f)
        game.font.draw(game.batch, "dummy X speed: " + almuBotDummy.speed.x, 0f, 420f)


        game.batch.draw(botImage, almuBotCircle.hitBox.x - almuBotCircle.hitBox.radius,
                almuBotCircle.hitBox.y - almuBotCircle.hitBox.radius,
                almuBotCircle.hitBox.radius * 2,
                almuBotCircle.hitBox.radius * 2)
        game.batch.draw(botImage, almuBotDummy.hitBox.x - almuBotCircle.hitBox.radius,
                almuBotDummy.hitBox.y - almuBotCircle.hitBox.radius,
                almuBotDummy.hitBox.radius * 2,
                almuBotDummy.hitBox.radius * 2)
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

        // check for collisions with other bots
        if (almuBotCircle.hitBox.overlaps(almuBotDummy.hitBox)) {
            val collisionAngle = almuBotCircle.speed.angle(almuBotDummy.speed)
            // collision from behind
            if (collisionAngle <= 90 && collisionAngle >= -90){
                if (almuBotCircle.speed.len() > almuBotDummy.speed.len()) {
                    almuBotCircle.speed = reflectedSpeed(almuBotCircle.speed, almuBotCircle.hitBox, almuBotDummy.hitBox)
                    almuBotCircle.hitBox.x = almuBotCircle.previousPosition.x
                    almuBotCircle.hitBox.y = almuBotCircle.previousPosition.y
                } else {
                    almuBotDummy.speed = reflectedSpeed(almuBotDummy.speed, almuBotCircle.hitBox, almuBotDummy.hitBox)
                    almuBotDummy.hitBox.x = almuBotDummy.previousPosition.x
                    almuBotDummy.hitBox.y = almuBotDummy.previousPosition.y
                }
            } else {
                almuBotCircle.speed = reflectedSpeed(almuBotCircle.speed, almuBotCircle.hitBox, almuBotDummy.hitBox)

                almuBotDummy.speed = reflectedSpeed(almuBotDummy.speed, almuBotCircle.hitBox, almuBotDummy.hitBox)

                almuBotCircle.hitBox.x = almuBotCircle.previousPosition.x
                almuBotCircle.hitBox.y = almuBotCircle.previousPosition.y

                almuBotDummy.hitBox.x = almuBotDummy.previousPosition.x
                almuBotDummy.hitBox.y = almuBotDummy.previousPosition.y
            }
        }

        // check if bot is in bounds
        if (almuBotOutOfBounds(almuBotCircle.hitBox, Constants.screenWidth, Constants.screenHeight)) {
            putBotBackToBounds(almuBotCircle, Constants.screenWidth, Constants.screenHeight)
        }

        // check if bot is in bounds
        if (almuBotOutOfBounds(almuBotDummy.hitBox, Constants.screenWidth, Constants.screenHeight)) {
            putBotBackToBounds(almuBotDummy, Constants.screenWidth, Constants.screenHeight)
        }

        almuBotCircle.previousPosition.x = almuBotCircle.hitBox.x
        almuBotCircle.previousPosition.y = almuBotCircle.hitBox.y
        almuBotCircle.hitBox.x += almuBotCircle.speed.x * delta
        almuBotCircle.hitBox.y += almuBotCircle.speed.y * delta

        almuBotDummy.previousPosition.x = almuBotDummy.hitBox.x
        almuBotDummy.previousPosition.y = almuBotDummy.hitBox.y
        almuBotDummy.hitBox.x += almuBotDummy.speed.x * delta
        almuBotDummy.hitBox.y += almuBotDummy.speed.y * delta
    }

    private fun putBotBackToBounds(almuBotCircle: AlmuBotSimple, screenWidth: Int, screenHeight: Int) {
        if (almuBotCircle.hitBox.x - almuBotCircle.hitBox.radius <= 0) {
            almuBotCircle.speed.x = -almuBotCircle.speed.x
        }
        if (almuBotCircle.hitBox.y - almuBotCircle.hitBox.radius <= 0) {
            almuBotCircle.speed.y = -almuBotCircle.speed.y
        }
        if (almuBotCircle.hitBox.x + almuBotCircle.hitBox.radius >=  screenWidth) {
            almuBotCircle.speed.x = -almuBotCircle.speed.x
        }
        if (almuBotCircle.hitBox.y + almuBotCircle.hitBox.radius >=  screenHeight) {
            almuBotCircle.speed.y = -almuBotCircle.speed.y
        }
    }

    private fun almuBotOutOfBounds(hitBox: Circle, screenWidth: Int, screenHeight: Int): Boolean {
        return (hitBox.x - hitBox.radius <= 0) ||
                (hitBox.y - hitBox.radius <= 0) ||
                (hitBox.x + hitBox.radius >=  screenWidth) ||
                (hitBox.y + hitBox.radius >=  screenHeight)
    }

    private fun reflectedSpeed(speed: Vector2, hitBox1: Circle, hitBox2: Circle): Vector2 {
        val m = (hitBox2.x - hitBox1.x) / (hitBox1.y - hitBox2.y)
        val newSpeed = Vector2()
        newSpeed.x = speed.x * ((1 - m * m) / (m * m + 1)) + speed.y * ((2 * m) / (m * m + 1))
        newSpeed.y = speed.x * ((2 * m) / (m * m + 1)) + speed.y * ((m * m - 1) / (m * m + 1))
        return newSpeed
    }

    override fun dispose() {
        botImage.dispose()
    }
}