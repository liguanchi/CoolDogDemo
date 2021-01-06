package com.example.cooldogdemo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start_up.*
import java.util.*

class StartUpActivity : AppCompatActivity() {
    private val mediaParser = MediaPlayer()
    private val handler = Handler(Looper.getMainLooper())
    private var time = 7
    private val timer = Timer()
    private val task: TimerTask = object : TimerTask() {
        override fun run() {
            runOnUiThread {
                time--
                button.text = "$time 跳过"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_up)

        //setTheme(R.style.MyTheme)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        initMediaPlayer()
        mediaParser.start()



        handler.postDelayed({
            imageView.setImageResource(R.drawable.poster)
            button.visibility = View.VISIBLE;
        }, 2000)

        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 7500)

        timer.schedule(task, 1000, 1000)

        button.setOnClickListener {
            handler.removeCallbacksAndMessages(null)
            init()
        }

    }

    private fun init() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initMediaPlayer() {
        val assetManager = assets
        val fd = assetManager.openFd("kugou.mp3")
        mediaParser.setDataSource(fd.fileDescriptor,fd.startOffset,fd.length)
        mediaParser.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaParser.stop()
        mediaParser.release()
    }


}