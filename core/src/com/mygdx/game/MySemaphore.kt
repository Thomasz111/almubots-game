package com.mygdx.game

import kotlinx.atomicfu.atomic
import java.util.concurrent.Semaphore

class MySemaphore(permits: Int = 1) : Semaphore(permits) {

    var isAcquired = atomic(false)

    fun acquire(changeState: Boolean = true) {
        if (changeState) {
            isAcquired.value = true
        }
        acquire()
    }
    override fun release() {
        super.release()
        isAcquired.value = false
    }
}