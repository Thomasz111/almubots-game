package com.mygdx.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.mygdx.game.Game
import com.mygdx.game.communication.Comm
import com.mygdx.game.utils.Constants

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "Almubots"
        config.width = Constants.screenWidth
        config.height = Constants.screenHeight
        Comm().start()
        LwjglApplication(Game(), config)
    }
}
