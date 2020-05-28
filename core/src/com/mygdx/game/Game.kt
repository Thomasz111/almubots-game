package com.mygdx.game

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.managers.BotsManager
import com.mygdx.game.managers.BulletsManager
import com.mygdx.game.screen.MainMenuScreen
import com.mygdx.game.utils.Constants
import kotlinx.coroutines.sync.Semaphore
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.inject.Context
import java.util.concurrent.CountDownLatch
import java.util.concurrent.CyclicBarrier

class Game : KtxGame<KtxScreen>() {
    private val context = Context()

    override fun create() {
        context.register {

            val fps = 10000
            val framesPerMillis = fps * 1000
            val millisPerFrame: Float = 1000.0f / framesPerMillis
            println(millisPerFrame)
            print("Pass number of bots: ")
//            addScreen(MainMenuScreen(BotsManager(), BulletsManager()))//.render(millisPerFrame))
            bindSingleton(this@Game)
            bindSingleton<Batch>(SpriteBatch())
            bindSingleton(BitmapFont())
//             The camera ensures we can render using our target resolution of 800x480
//                pixels no matter what the screen resolution is.
            bindSingleton(OrthographicCamera().apply { setToOrtho(false, Constants.screenWidth.toFloat(), Constants.screenHeight.toFloat()) })
            bindSingleton(BotsManager())
            bindSingleton(BulletsManager())

            addScreen(MainMenuScreen(inject(), inject(), inject(), inject(), inject(), inject()))
//        }
            setScreen<MainMenuScreen>()
            super.create()
        }
    }
        override fun dispose() {
            context.dispose()
//        super.dispose()
        }
    }

object GameObj {
    lateinit var semaphores: List<MySemaphore>
    var numOfBots: Int = 0
}
