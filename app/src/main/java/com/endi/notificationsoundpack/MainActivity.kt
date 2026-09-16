package com.endi.notificationsoundpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.endi.soundpack.SoundPackPlayer
import com.endi.soundpack.VoicePackManager
import com.endi.soundpack.model.Voice
import kotlinx.coroutines.launch

/**
 * @author Endi
 * @version 1.0
 * @since 9/10/2026
 */

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            try {
                // Download dan install voice Male
                VoicePackManager.downloadMalePack(this@MainActivity)

                // Pakai voice Male
                SoundPackPlayer.setVoice(Voice.MALE)

                // Test suara nominal
                SoundPackPlayer.playAmount(
                    context = this@MainActivity,
                    amount = 12_500
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        SoundPackPlayer.stop()
        super.onDestroy()
    }
}