package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.mygdx.game.Game
import ktx.app.KtxScreen
import ktx.graphics.use

class MainMenuScreen(private val game: Game,
                     private val batch: Batch,
                     private val font: BitmapFont,
                     private val camera: OrthographicCamera) : KtxScreen {
    override fun render(delta: Float) {
        camera.update()
        batch.projectionMatrix = camera.combined

        batch.use {
            font.draw(it, "Welcome to ALMUBOTS!!! ", 100f, 150f)
            font.draw(it, "press 1 to test simple physics", 100f, 110f)
            font.draw(it, "press 2 to test simpler physics", 100f, 70f)
            font.draw(it, "press 3 to test not so simple physics", 100f, 30f)
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            game.addScreen(GameScreenSimple(batch, font, camera))
            game.setScreen<GameScreenSimple>()
            game.removeScreen<MainMenuScreen>()
            dispose()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            game.addScreen(GameScreenSimpler(batch, font, camera))
            game.setScreen<GameScreenSimpler>()
            game.removeScreen<MainMenuScreen>()
            dispose()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            game.addScreen(GameScreenNotSimple(batch, font, camera))
            game.setScreen<GameScreenNotSimple>()
            game.removeScreen<MainMenuScreen>()
            dispose()
        }
    }
}