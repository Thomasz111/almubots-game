package com.mygdx.game.managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.mygdx.game.gameobjects.AlmuBotSimple
import com.mygdx.game.gameobjects.Bullet
import com.mygdx.game.physics.CirclePhysicsSimple
import com.mygdx.game.utils.Constants
import java.nio.file.Paths
import kotlin.math.cos
import kotlin.math.sin

class BulletsManager {
    private val texturesPath = Paths.get("assets/textures").toAbsolutePath().toString()
    private val bulletImage = Texture(Gdx.files.internal("$texturesPath/test.png"))
    private val bulletsOnScreen = Array<Bullet>()
    private val bulletRadius = 10f
    private val bulletSpeed = 700f

    fun spawnBullet(botId: Int, x: Float, y: Float, rotation: Float) {
        val speed = Vector2(bulletSpeed * cos(rotation * Math.PI / 180).toFloat(), bulletSpeed * sin(rotation * Math.PI / 180).toFloat())
        val bullet = Bullet(botId, Circle(x, y, bulletRadius), bulletImage, CirclePhysicsSimple(), speed, rotation)
        bulletsOnScreen.add(bullet)
    }

    fun destroyBullet(bullet: Bullet) {
        bulletsOnScreen.removeValue(bullet, true)
    }

    fun drawBullets(batch: Batch) {
        bulletsOnScreen.forEach { bullet -> bullet.draw(batch) }
    }

    fun manageCollisionsWithWalls() {
        bulletsOnScreen.forEach { bullet ->
            if (bullet.outOfBounds(Constants.screenWidth, Constants.screenHeight)) {
                destroyBullet(bullet)
            }
        }
    }

    fun manageCollisionsWithBots(bots: Array<AlmuBotSimple>) {
        bots.forEach { bot ->
            if (!bot.dead) {
                bulletsOnScreen.forEach { bullet ->
                    if (bullet.collisionOccurredWith(bot.hitBox)) {
                        bot.manageCollisionWith(bullet)
                        destroyBullet(bullet)
                    }
                }
            }
        }
    }

    fun updateBullets(delta: Float) {
        bulletsOnScreen.forEach { bullet -> bullet.update(delta) }
    }
}