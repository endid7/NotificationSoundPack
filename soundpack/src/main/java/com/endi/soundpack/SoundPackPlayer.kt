package com.endi.soundpack

import android.content.Context
import android.media.MediaPlayer
import com.endi.soundpack.model.Voice

/**
 * @author Endi
 * @version 1.0
 * @since 9/10/2026
 */

object SoundPackPlayer {

    private var mediaPlayer: MediaPlayer? = null

    private var audioSources: List<AudioSource> = emptyList()
    private var currentIndex = 0

    private var currentVoice = Voice.FEMALE

    private var onComplete: (() -> Unit)? = null

    /**
     * Set voice yang akan digunakan untuk playback.
     */
    fun setVoice(voice: Voice) {
        currentVoice = voice
    }

    /**
     * Voice yang sedang digunakan.
     */
    fun getVoice(): Voice {
        return currentVoice
    }

    fun isVoiceAvailable(
        context: Context,
        voice: Voice
    ): Boolean {
        return VoicePackManager.isVoicePackAvailable(
            context = context,
            voice = voice
        )
    }

    fun playAmount(
        context: Context,
        amount: Long,
        onComplete: (() -> Unit)? = null
    ) {
        stop()

        val resourceNames = IndonesianNumber.toAudioNames(amount)

        audioSources = resourceNames.mapNotNull { resourceName ->
            VoiceAudioResolver.resolve(
                context = context.applicationContext,
                voice = currentVoice,
                resourceName = resourceName
            )
        }

        if (audioSources.isEmpty()) {
            throw IllegalStateException(
                "No audio available for voice: $currentVoice"
            )
        }

        if (currentVoice == Voice.MALE &&
            audioSources.size != resourceNames.size
        ) {
            throw IllegalStateException(
                "Male voice pack is incomplete. " +
                        "Please download the male voice pack first."
            )
        }

        currentIndex = 0
        this.onComplete = onComplete

        playNext(context.applicationContext)
    }

    fun playPaymentReceived(
        context: Context,
        onComplete: (() -> Unit)? = null
    ) {
        stop()

        val resourceName = "payment_received"

        val source = VoiceAudioResolver.resolve(
            context = context.applicationContext,
            voice = currentVoice,
            resourceName = resourceName
        )

        if (source == null) {
            throw IllegalStateException(
                "Payment received audio is not available " +
                        "for voice: $currentVoice"
            )
        }

        audioSources = listOf(source)
        currentIndex = 0
        this.onComplete = onComplete

        playNext(context.applicationContext)
    }

    fun playPaymentReceivedMyIndopay(
        context: Context,
        onComplete: (() -> Unit)? = null
    ) {
        stop()

        val resourceName = "payment_received_myindopay"

        val source = VoiceAudioResolver.resolve(
            context = context.applicationContext,
            voice = currentVoice,
            resourceName = resourceName
        )

        if (source == null) {
            throw IllegalStateException(
                "Payment received MyIndopay audio is not available " +
                        "for voice: $currentVoice"
            )
        }

        audioSources = listOf(source)
        currentIndex = 0
        this.onComplete = onComplete

        playNext(context.applicationContext)
    }

    private fun playNext(context: Context) {
        if (currentIndex >= audioSources.size) {
            finish()
            return
        }

        val source = audioSources[currentIndex]

        val player = when (source) {
            is AudioSource.Resource -> {
                MediaPlayer.create(
                    context,
                    source.resourceId
                )
            }

            is AudioSource.File -> {
                MediaPlayer().apply {
                    setDataSource(source.file.absolutePath)
                    prepare()
                }
            }
        }

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

        audioSources = emptyList()
        currentIndex = 0
        onComplete = null
    }

    fun release() {
        stop()
    }

    private fun finish() {
        audioSources = emptyList()
        currentIndex = 0

        val callback = onComplete
        onComplete = null

        callback?.invoke()
    }
}