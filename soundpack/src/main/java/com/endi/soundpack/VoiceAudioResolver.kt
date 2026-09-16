package com.endi.soundpack

import android.content.Context
import com.endi.soundpack.model.Voice

/**
 * @author Endi
 * @version 1.0
 * @since 9/16/2026
 */

internal object VoiceAudioResolver {

    fun resolve(
        context: Context,
        voice: Voice,
        resourceName: String
    ): AudioSource? {

        return when (voice) {
            Voice.FEMALE -> {
                AudioSource.Resource(
                    resourceId = resourceId(resourceName)
                )
            }

            Voice.MALE -> {
                VoicePackManager
                    .getAudioFile(
                        context = context,
                        voice = voice,
                        resourceName = resourceName
                    )
                    ?.let { AudioSource.File(it) }
            }
        }
    }

    private fun resourceId(
        resourceName: String
    ): Int {
        return when (resourceName) {
            "nol" -> R.raw.nol
            "satu" -> R.raw.satu
            "dua" -> R.raw.dua
            "tiga" -> R.raw.tiga
            "empat" -> R.raw.empat
            "lima" -> R.raw.lima
            "enam" -> R.raw.enam
            "tujuh" -> R.raw.tujuh
            "delapan" -> R.raw.delapan
            "sembilan" -> R.raw.sembilan
            "sepuluh" -> R.raw.sepuluh
            "sebelas" -> R.raw.sebelas
            "belas" -> R.raw.belas
            "puluh" -> R.raw.puluh
            "ratus" -> R.raw.ratus
            "seratus" -> R.raw.seratus
            "ribu" -> R.raw.ribu
            "seribu" -> R.raw.seribu
            "juta" -> R.raw.juta
            "miliar" -> R.raw.miliar
            "triliun" -> R.raw.triliun
            "rupiah" -> R.raw.rupiah
            "payment_received" -> R.raw.payment_received
            "payment_received_indopay" -> R.raw.payment_received_indopay
            "payment_received_myindopay" -> R.raw.payment_received_myindopay
            "custom_notification_sound" -> R.raw.custom_notification_sound

            else -> throw IllegalArgumentException(
                "Unknown sound resource: $resourceName"
            )
        }
    }
}

internal sealed class AudioSource {

    data class Resource(
        val resourceId: Int
    ) : AudioSource()

    data class File(
        val file: java.io.File
    ) : AudioSource()
}