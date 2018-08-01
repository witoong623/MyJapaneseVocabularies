package net.aliveplex.witoong623.myjapanesevocabularies.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

object SingleThreadExecutor {
    private val executor: Executor = Executors.newSingleThreadExecutor()

    fun execute(task: Runnable) {
        executor.execute(task)
    }
}