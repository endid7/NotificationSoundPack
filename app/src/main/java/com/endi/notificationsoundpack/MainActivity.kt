package com.endi.notificationsoundpack

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.endi.soundpack.SoundPackPlayer
import com.endi.soundpack.VoicePackManager
import com.endi.soundpack.model.Voice
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "SoundPackTest"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            try {
                Log.d(TAG, "1. Start download")

                VoicePackManager.downloadMalePack(this@MainActivity)

                Log.d(TAG, "2. Download/install success")

                val available = VoicePackManager.isVoicePackAvailable(
                    this@MainActivity,
                    Voice.MALE
                )

                Log.d(TAG, "3. Male available = $available")

                if (!available) {
                    throw IllegalStateException("Male voice pack tidak tersedia setelah install")
                }

                SoundPackPlayer.setVoice(Voice.MALE)

                Log.d(TAG, "4. Voice = ${SoundPackPlayer.getVoice()}")

                Toast.makeText(
                    this@MainActivity,
                    "Playing 672.500",
                    Toast.LENGTH_SHORT
                ).show()

                SoundPackPlayer.playAmount(
                    context = this@MainActivity,
                    amount = 672500
                )

                Log.d(TAG, "5. playAmount called")

            } catch (e: Exception) {
                Log.e(TAG, "SoundPack test FAILED", e)

                Toast.makeText(
                    this@MainActivity,
                    "SoundPack error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroy() {
        SoundPackPlayer.stop()
        super.onDestroy()
    }
}