package com.mygdx.game

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.managers.BulletsManager
import com.mygdx.game.screen.MainMenuScreen
import com.mygdx.game.utils.Constants
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.inject.Context

class Game : KtxGame<KtxScreen>() {
    private val context = Context()

    override fun create() {
        context.register {
            bindSingleton(this@Game)
            bindSingleton<Batch>(SpriteBatch())
            bindSingleton(BitmapFont())
            // The camera ensures we can render using our target resolution of 800x480
            //    pixels no matter what the screen resolution is.
            bindSingleton(OrthographicCamera().apply { setToOrtho(false, Constants.screenWidth.toFloat(), Constants.screenHeight.toFloat()) })
            bindSingleton(BulletsManager())

            addScreen(MainMenuScreen(inject(), inject(), inject(), inject(), inject()))
        }
        setScreen<MainMenuScreen>()
        super.create()
    }

    override fun dispose() {
        context.dispose()
        super.dispose()
    }
}
