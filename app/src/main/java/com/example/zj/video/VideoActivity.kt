package com.example.zj.video

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.example.zj.androidstudy.R
import android.widget.VideoView

class VideoActivity : AppCompatActivity() {
    private var mVideoView: VideoView? = null
    private var mMediaController: MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        mVideoView = findViewById(R.id.video_view)

        init()
    }

    private fun init() {
        val path = "https://media.w3.org/2010/05/sintel/trailer.mp4"
        val uri: Uri = Uri.parse(path)
        mMediaController = MediaController(this)
        mVideoView?.setVideoPath(path)
        mVideoView?.setMediaController(mMediaController)
        mVideoView?.seekTo(0)
        mVideoView?.start()
        mVideoView?.requestFocus()
    }
}