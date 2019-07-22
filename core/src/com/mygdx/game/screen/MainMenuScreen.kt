package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.mygdx.game.Game
import com.mygdx.game.utils.Constants
import ktx.app.KtxScreen

class MainMenuScreen(val game: Game) : KtxScreen {
    private val camera = OrthographicCamera().apply { setToOrtho(false, Constants.screenWidth.toFloat(), Constants.screenHeight.toFloat()) }

    override fun render(delta: Float) {
        camera.update()
        game.batch.projectionMatrix = camera.combined

        game.batch.begin()
        game.font.draw(game.batch, "Welcome to ALMUBOTS!!! ", 100f, 150f)
        game.font.draw(game.batch, "press 1 to test simple physics", 100f, 100f)
        game.font.draw(game.batch, "press 2 to test simpler physics", 100f, 50f)
        game.batch.end()

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            game.addScreen(GameScreenSimple(game))
            game.setScreen<GameScreenSimple>()
            game.removeScreen<MainMenuScreen>()
            dispose()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            game.addScreen(GameScreenSimpler(game))
            game.setScreen<GameScreenSimpler>()
            game.removeScreen<MainMenuScreen>()
            dispose()
        }
    }
}