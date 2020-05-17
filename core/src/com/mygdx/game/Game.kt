package com.mygdx.game

import com.mygdx.game.managers.BotsManager
import com.mygdx.game.managers.BulletsManager
import com.mygdx.game.screen.MainMenuScreen
import ktx.inject.Context

class Game {
    //: KtxGame<KtxScreen>() {
    private val context = Context()

    fun start() {
//        context.register {

        val fps = 10000
        val framesPerMillis = fps * 1000
        val millisPerFrame: Float = 1000.0f / framesPerMillis
        println(millisPerFrame)
        print("Pass number of bots: ")
        MainMenuScreen(BotsManager(), BulletsManager()).render(millisPerFrame)
//            bindSingleton(this@Game)
//            bindSingleton<Batch>(SpriteBatch())
//            bindSingleton(BitmapFont())
            // The camera ensures we can render using our target resolution of 800x480
            //    pixels no matter what the screen resolution is.
//            bindSingleton(OrthographicCamera().apply { setToOrtho(false, Constants.screenWidth.toFloat(), Constants.screenHeight.toFloat()) })
//            bindSingleton(BotsManager())
//            bindSingleton(BulletsManager())

//            addScreen(MainMenuScreen(inject(), inject(), inject(), inject(), inject(), inject()))
//        }
//        setScreen<MainMenuScreen>()
//        super.create()
    }

    fun dispose() {
        context.dispose()
//        super.dispose()
    }
}
