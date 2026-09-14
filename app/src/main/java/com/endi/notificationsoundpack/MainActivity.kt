package com.endi.notificationsoundpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.endi.soundpack.SoundPackPlayer

/**
 * @author Endi
 * @version 1.0
 * @since 9/10/2026
 */

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SoundPackPlayer.playPaymentReceived(
            context = this,
            onComplete = {
                SoundPackPlayer.playAmount(
                    this,
                    amount = 12500
                )
            }
        )
    }

    override fun onDestroy() {
        SoundPackPlayer.stop()
        super.onDestroy()
    }
}