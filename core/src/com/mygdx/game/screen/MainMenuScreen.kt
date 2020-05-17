package com.mygdx.game.screen

import com.mygdx.game.managers.BotsManager
import com.mygdx.game.managers.BulletsManager
import com.mygdx.game.physics.CirclePhysicsMomentum
import java.util.*
import kotlin.concurrent.schedule

class MainMenuScreen(
                     private val botsManager: BotsManager,
                     private val bulletsManager: BulletsManager) { //}: KtxScreen {

    private var numOfBots = readLine()!!.toInt()

    private val gameScreen: GameScreen = GameScreen(botsManager, bulletsManager)
    private val timer = Timer()
    private var delta: Float = 0.001f
    private var deltaMillis: Long = 1L

    fun render(delta: Float) {

//        camera.update()
//        batch.projectionMatrix = camera.combined

//        batch.use {
//            font.draw(it, "Welcome to ALMUBOTS!!! ", 100f, 150f)
//            font.draw(it, "press 1 to test simple physics", 100f, 110f)
//            font.draw(it, "press 2 to test simpler physics", 100f, 70f)
//            font.draw(it, "press 3 to test not so simple physics", 100f, 30f)
//        }


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
        val physics = CirclePhysicsMomentum()
        botsManager.initBots(numOfBots, physics, bulletsManager)

//        val calendar = Calendar.getInstance()
//        var now = calendar.timeInMillis

        this.delta = delta
        this.deltaMillis = (delta * 1000).toLong()
        timer.schedule(deltaMillis) {
            endlessLoop()
        }

//        while(true) {
//            if(calendar.timeInMillis > now + delta * 1000) {
//                now = calendar.timeInMillis
//            }
//        }

//        game.addScreen(GameScreen(batch, font, camera, botsManager, bulletsManager))
//            game.setScreen<GameScreen>()
//            game.removeScreen<MainMenuScreen>()
//            dispose()
//        }
    }

    fun endlessLoop() {
        gameScreen.render(delta)
        timer.schedule(deltaMillis) {
            endlessLoop()
        }
    }
}