package com.mygdx.game.screen

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.mygdx.game.Game
import com.mygdx.game.GameObj
import com.mygdx.game.MySemaphore
import com.mygdx.game.managers.BotsManager
import com.mygdx.game.managers.BulletsManager
import com.mygdx.game.physics.CirclePhysicsMomentum
import ktx.app.KtxScreen
import ktx.graphics.use
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.schedule

class MainMenuScreen(private val game: Game,
                     private val batch: Batch,
                     private val font: BitmapFont,
                     private val camera: OrthographicCamera,
                     private val botsManager: BotsManager,
                     private val bulletsManager: BulletsManager
    ): KtxScreen {

    private var numOfBots = 2 //readLine()!!.toInt()

//    private val gameScreen: GameScreen = GameScreen(botsManager, bulletsManager)
    private val timer = Timer()
    private var delta: Float = 0.001f
    private var deltaMillis: Long = 1L
    private lateinit var januszGameScreen: GameScreenWithoutScreen

    private var done = false

    override fun render(delta: Float) {

        camera.update()
        batch.projectionMatrix = camera.combined

        batch.use {
            font.draw(it, "Welcome to ALMUBOTS!!! ", 100f, 150f)
            font.draw(it, "press 1 to test simple physics", 100f, 110f)
            font.draw(it, "press 2 to test simpler physics", 100f, 70f)
            font.draw(it, "press 3 to test not so simple physics", 100f, 30f)
        }
//
//
//        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
//            val physics = CirclePhysicsHeavy()
//            botsManager.initBots(numOfBots, physics, bulletsManager)
//            game.addScreen(GameScreen(batch, font, camera, botsManager, bulletsManager))
//            game.setScreen<GameScreen>()
//            game.removeScreen<MainMenuScreen>()
//            dispose()
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
//            val physics = CirclePhysicsSimple()
//            botsManager.initBots(numOfBots, physics, bulletsManager)
//            game.addScreen(GameScreen(batch, font, camera, botsManager, bulletsManager))
//            game.setScreen<GameScreen>()
//            game.removeScreen<MainMenuScreen>()
//            dispose()
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
        if (!done) {
            val physics = CirclePhysicsMomentum()
            GameObj.numOfBots = numOfBots
            GameObj.semaphores = List(numOfBots) { MySemaphore() }
            GameObj.semaphores.forEach { latch -> latch.acquire(false) }
            botsManager.initBots(numOfBots, physics, bulletsManager)

//        val calendar = Calendar.getInstance()
//        var now = calendar.timeInMillis

//        this.delta = delta
//        this.deltaMillis = (delta * 1000).toLong()
//        timer.schedule(deltaMillis) {
//            endlessLoop()
//        }
//        Thread { endlessLoop() }.start()

//        while(true) {
//            if(calendar.timeInMillis > now + delta * 1000) {
//                now = calendar.timeInMillis
//            }
//        }
            val JANUSZ_MODE = true

            if (JANUSZ_MODE) {
                januszGameScreen = GameScreenWithoutScreen(botsManager, bulletsManager)
                endlessLoop()
            } else {
                game.addScreen(GameScreen(batch, font, camera, botsManager, bulletsManager))
                game.setScreen<GameScreen>()
                game.removeScreen<MainMenuScreen>()
                dispose()
            }
            done = true
        }
    }

    fun endlessLoop() {
//        if (Synchronizer.numOfBotsResponses.value >= GameObj.numOfBots) {
//        println("Available permits: " + GameObj.latches[0].count)
        if (GameObj.semaphores.all{semaphore -> semaphore.isAcquired.value}) {
//            Synchronizer.numOfBotsResponses.value = 0
//            println("Rendering game")
            januszGameScreen.render()
        }
        timer.schedule(deltaMillis) {
            endlessLoop()
        }
    }
}