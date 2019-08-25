package com.mygdx.game.managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Circle
import com.mygdx.game.communication.CleanCommand
import com.mygdx.game.communication.Command
import com.mygdx.game.communication.GameStatus
import com.mygdx.game.gameobjects.AlmuBotSimple
import com.mygdx.game.gameobjects.Gun
import com.mygdx.game.physics.CirclePhysics
import com.mygdx.game.utils.Constants
import java.nio.file.Paths
import java.util.*

class BotsManager {
    private val texturesPath = Paths.get("assets/textures").toAbsolutePath().toString()
    var bots = emptyArray<AlmuBotSimple>()
        private set
    private val botRadius = 32f

    fun initBots(botsNum: Int, physics: CirclePhysics, bulletsManager: BulletsManager) {
        bots = Array(botsNum) { botNum ->
            val hitBox = Circle(
                    botRadius + Random().nextInt(Constants.screenWidth - botRadius.toInt()),
                    botRadius + Random().nextInt(Constants.screenHeight - botRadius.toInt()),
                    botRadius
            )
            val botImage = Texture(Gdx.files.internal("$texturesPath/bot/bot$botNum.png"))
            val gunImage = Texture(Gdx.files.internal("$texturesPath/gun/gun$botNum.png"))
            AlmuBotSimple(botNum, hitBox, botImage, physics, Gun(gunImage, bulletsManager,hitBox.radius * 1.5f, hitBox.radius * 0.5f))
        }
    }

    fun manageRespawn(delta: Float) {
        bots.forEach { it.manageRespawn(delta) }
    }

    fun drawBots(batch: Batch) {
        bots.forEach { bot -> bot.draw(batch) }
    }

    fun processCommands(cmds: List<Command>) {
        cmds.forEach { dirtyCmd ->
            val cmd = CleanCommand(dirtyCmd)
            val bot = bots[cmd.botNo]
            if (!bot.dead) {
                bot.speed.x += 10 * cmd.dx
                bot.speed.y += 10 * cmd.dy
                if (cmd.shoot) {
                    bot.testShooting()
                }
                bot.rotateGun(cmd.rotation)
            }
        }
    }

    fun manageCollisionsWithBots() {
        for (bot1 in bots) {
            for (bot2 in bots) {
                if (bot1 != bot2) {
                    if (bot1.collisionOccurredWith(bot2.hitBox)) {
                        bot1.manageCollisionWith(bot2)
                    }
                }
            }
        }
    }

    fun manageCollisionsWithWalls() {
        bots.forEach { bot ->
            if (!bot.dead && bot.outOfBounds(Constants.screenWidth, Constants.screenHeight)) {
                bot.putBotBackToBounds(Constants.screenWidth, Constants.screenHeight)
            }
        }
    }

    fun updateBots(delta: Float) {
        bots.forEach { bot -> bot.update(delta) }
    }

    fun clearShoots() {
        bots.forEach { it.shoot = false }
    }

    fun getStatus() = bots.map { bot ->
        GameStatus.BotStatus(
            bot.hitBox.x, bot.hitBox.y, bot.speed.x, bot.speed.y, bot.gun.rotation, bot.ammo, bot.life, bot.shoot)
    }

    fun processUserInputs() {
        if (bots.isNotEmpty() && !bots[0].dead) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                bots[0].speed.x -= 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                bots[0].speed.x += 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                bots[0].speed.y += 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                bots[0].speed.y -= 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.V)) {
                bots[0].rotateGun(1)
            }
            if (Gdx.input.isKeyPressed(Input.Keys.B)) {
                bots[0].testShooting()
            }
        }
        if (bots.size >= 2 && !bots[1].dead) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                bots[1].speed.x -= 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                bots[1].speed.x += 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                bots[1].speed.y += 10
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                bots[1].speed.y -= 10
            }
        }
    }

    fun printDiagnostics(batch: Batch, font: BitmapFont) {
        if (bots.isNotEmpty()) {
            font.draw(batch, "bot1 score: " + bots[0].score, 0f, 480f)
            font.draw(batch, "bot1 life: ${bots[0].life}", 0f, 460f)
            font.draw(batch, "bot1 ammo: ${bots[0].ammo}", 0f, 440f)
        }

        if (bots.size >= 2) {
            font.draw(batch, "bot2 score: " + bots[1].score, 0f, 400f)
            font.draw(batch, "bot2 life: ${bots[1].life}", 0f, 380f)
            font.draw(batch, "bot2 ammo: ${bots[1].ammo}", 0f, 360f)
        }
    }
}