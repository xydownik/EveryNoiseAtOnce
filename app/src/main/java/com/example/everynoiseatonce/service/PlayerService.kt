package com.example.everynoiseatonce.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.everynoiseatonce.R
import com.example.everynoiseatonce.di.DaggerServiceComponent
import com.example.everynoiseatonce.domain.model.Track
import com.example.everynoiseatonce.utils.ExoPlayerHolder
import javax.inject.Inject

class PlayerService : Service() {

    @Inject
    lateinit var exoPlayerHolder: ExoPlayerHolder

    private var currentTrack: Track? = null

    override fun onCreate() {
        super.onCreate()
        DaggerServiceComponent.factory().create(applicationContext).inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action

        when (action) {
            ACTION_STOP -> {
                stopSelf()
                return START_NOT_STICKY
            }

            ACTION_PLAY -> {
                val track = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getSerializableExtra("track", Track::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getSerializableExtra("track") as? Track
                }

                // Если трек другой — перезапускаем
                if (track != null && track != currentTrack) {
                    currentTrack = track
                    exoPlayerHolder.play(track.preview_url ?: return START_NOT_STICKY)
                    startForeground(1, createNotification(track))
                } else if (track == currentTrack) {
                    // Повторное нажатие — стоп
                    stopSelf()
                }
            }
        }

        return START_NOT_STICKY
    }


    private fun createNotification(track: Track): Notification {
        val channelId = "track_channel"
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(channelId, "Track Playback", NotificationManager.IMPORTANCE_LOW)
        manager.createNotificationChannel(channel)

        val stopIntent = Intent(this, PlayerService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(
            this,
            1,
            stopIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Now playing")
            .setContentText(track.name)
            .setSmallIcon(R.drawable.play)
            .addAction(R.drawable.pause, "Stop", stopPendingIntent)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .build()
    }


    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        exoPlayerHolder.stop()
        super.onDestroy()
    }
    companion object {
        const val ACTION_PLAY = "com.example.everynoiseatonce.PLAY"
        const val ACTION_STOP = "com.example.everynoiseatonce.STOP"
    }
}
