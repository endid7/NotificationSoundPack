package com.endi.soundpack

import android.content.Context
import android.media.MediaPlayer

/**
 * @author Endi
 * @version 1.0
 * @since 9/10/2026
 */

object SoundPackPlayer {

    private var mediaPlayer: MediaPlayer? = null

    private var resources: List<Int> = emptyList()
    private var currentIndex = 0

    private var onComplete: (() -> Unit)? = null

    fun playAmount(
        context: Context,
        amount: Long,
        onComplete: (() -> Unit)? = null
    ) {
        stop()

        resources = IndonesianNumber.toAudioResources(amount)
        currentIndex = 0
        this.onComplete = onComplete

        playNext(context.applicationContext)
    }

    fun playPaymentReceived(
        context: Context,
        onComplete: (() -> Unit)? = null
    ) {
        stop()

        resources = listOf(R.raw.payment_received)
        currentIndex = 0
        this.onComplete = onComplete

        playNext(context.applicationContext)
    }

    fun playPaymentReceivedMyIndopay(
        context: Context,
        onComplete: (() -> Unit)? = null
    ) {
        stop()

        resources = listOf(R.raw.payment_received_indopay)
        currentIndex = 0
        this.onComplete = onComplete

        playNext(context.applicationContext)
    }

    private fun playNext(context: Context) {
        if (currentIndex >= resources.size) {
            finish()
            return
        }

        val resourceId = resources[currentIndex]

        val player = MediaPlayer.create(
            context,
            resourceId
        )

        if (player == null) {
            currentIndex++
            playNext(context)
            return
        }

        mediaPlayer = player

        player.setOnCompletionListener {
            it.release()

            if (mediaPlayer === it) {
                mediaPlayer = null
            }

            currentIndex++

            playNext(context)
        }

        player.setOnErrorListener { mp, _, _ ->
            mp.release()

            if (mediaPlayer === mp) {
                mediaPlayer = null
            }

            currentIndex++

            playNext(context)

            true
        }

        player.start()
    }

    fun stop() {
        mediaPlayer?.let { player ->
            try {
                if (player.isPlaying) {
                    player.stop()
                }
            } catch (_: IllegalStateException) {
            }

            player.release()
        }

        mediaPlayer = null

        resources = emptyList()
        currentIndex = 0
        onComplete = null
    }

    fun release() {
        stop()
    }

    private fun finish() {
        resources = emptyList()
        currentIndex = 0

        val callback = onComplete
        onComplete = null

        callback?.invoke()
    }
}