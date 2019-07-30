package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.mygdx.game.Game
import com.mygdx.game.managers.BulletsManager
import com.mygdx.game.physics.CirclePhysicsHeavy
import com.mygdx.game.physics.CirclePhysicsMomentum
import com.mygdx.game.physics.CirclePhysicsSimple
import ktx.app.KtxScreen
import ktx.graphics.use

class MainMenuScreen(private val game: Game,
                     private val batch: Batch,
                     private val font: BitmapFont,
                     private val camera: OrthographicCamera,
                     private val bulletsManager: BulletsManager) : KtxScreen {
    override fun render(delta: Float) {
        camera.update()
        batch.projectionMatrix = camera.combined

        batch.use {
            font.draw(it, "Welcome to ALMUBOTS!!! ", 100f, 150f)
            font.draw(it, "press 1 to test simple physics", 100f, 110f)
            font.draw(it, "press 2 to test simpler physics", 100f, 70f)
            font.draw(it, "press 3 to test not so simple physics", 100f, 30f)
        }

        val numOfBots = 2

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            game.addScreen(GameScreen(batch, font, camera, bulletsManager, CirclePhysicsHeavy(), numOfBots))
            game.setScreen<GameScreen>()
            game.removeScreen<MainMenuScreen>()
            dispose()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            game.addScreen(GameScreen(batch, font, camera, bulletsManager, CirclePhysicsSimple(), numOfBots))
            game.setScreen<GameScreen>()
            game.removeScreen<MainMenuScreen>()
            dispose()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            game.addScreen(GameScreen(batch, font, camera, bulletsManager, CirclePhysicsMomentum(), numOfBots))
            game.setScreen<GameScreen>()
            game.removeScreen<MainMenuScreen>()
            dispose()
        }
    }
}