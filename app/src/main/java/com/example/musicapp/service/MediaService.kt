package com.example.musicapp.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.musicapp.R

class MediaService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        // Создаем ExoPlayer
        val player = ExoPlayer.Builder(this).build()
        // Создаем MediaSession
        mediaSession = MediaSession.Builder(this, player).build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            "PLAY" -> mediaSession?.player?.play()
            "PAUSE" -> mediaSession?.player?.pause()
            "STOP" -> stopSelf()
            else -> {
                val musicUrl = intent?.getStringExtra("MUSIC_URL")
                if (musicUrl != null) {
                    val mediaItem = MediaItem.fromUri(musicUrl)
                    mediaSession?.player?.setMediaItem(mediaItem)
                    mediaSession?.player?.prepare()
                   // showNotification()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
    @SuppressLint("ForegroundServiceType")
    private fun showNotification() {
        val playIntent = Intent(this, MediaService::class.java).apply { action = "PLAY" }
        val playPendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val pauseIntent = Intent(this, MediaService::class.java).apply { action = "PAUSE" }
        val pausePendingIntent = PendingIntent.getService(this, 1, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val stopIntent = Intent(this, MediaService::class.java).apply { action = "STOP" }
        val stopPendingIntent = PendingIntent.getService(this, 2, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, "media_channel")
            .setContentTitle("Music Player")
            .setContentText("Playing track")
            .setSmallIcon(androidx.media3.session.R.drawable.media3_notification_small_icon)
            .addAction(androidx.media3.session.R.drawable.media3_icon_play, "Play", playPendingIntent)
            .addAction(androidx.media3.session.R.drawable.media3_icon_pause, "Pause", pausePendingIntent)
            .addAction(androidx.media3.session.R.drawable.media3_icon_stop, "Stop", stopPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()

        startForeground(1, notification)
    }
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }
}