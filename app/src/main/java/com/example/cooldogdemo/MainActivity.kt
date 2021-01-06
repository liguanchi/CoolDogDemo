package com.example.cooldogdemo

import android.app.Activity
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var index = 0
    private val itemList = ArrayList<ItemClass>()
    private val mediaParser = MediaPlayer()
//    private val num = mediaParser.duration

    private fun initItem() {
        repeat(1) {
            itemList.add(ItemClass("小尖尖", "薛之谦", R.drawable.music_01, "music_01.mp3"))
            itemList.add(ItemClass("彩券", "薛之谦", R.drawable.music_02, "music_02.mp3"))
            itemList.add(ItemClass("绅士", "薛之谦", R.drawable.music_03, "music_03.mp3"))
            itemList.add(ItemClass("天后（Live）", "薛之谦", R.drawable.music_04, "music_04.mp3"))
            itemList.add(ItemClass("天外来物", "薛之谦", R.drawable.music_05, "music_05.mp3"))
            itemList.add(ItemClass("下雨了", "薛之谦", R.drawable.music_06, "music_06.mp3"))
            itemList.add(ItemClass("演员", "薛之谦", R.drawable.music_07, "music_07.mp3"))
            itemList.add(ItemClass("野心", "薛之谦", R.drawable.music_08, "music_08.mp3"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fullScreen(this)

        initItem()

        initMediaPlayer(itemList[index].music)



        //设置线性布局管理器到控件
        val layoutManager = LinearLayoutManager(this)
        RecyclerViewTest.layoutManager = layoutManager
        val adapter = ItemAdapter(itemList)
        RecyclerViewTest.adapter = adapter


        musicPlay.setOnClickListener {
            if (!mediaParser.isPlaying) {
                musicPlay.setImageResource(R.drawable.start)
                mediaParser.start()
            } else {
                musicPlay.setImageResource(R.drawable.bofang)
                mediaParser.pause()
            }
        }

        nextSong.setOnClickListener {
            index++
            mediaParser.reset()
            if (index > itemList.size -1){
                index = 0
            }
            musicManager()

        }

        lastSong.setOnClickListener {
            index--
            mediaParser.reset()
            if (index < 0){
                index = itemList.size -1
            }
            musicManager()

        }



    }

    private fun zhuang(int:Int){
        val translateAnimation = RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        translateAnimation.duration = 10000
        translateAnimation.repeatCount = int / 10000
        translateAnimation.repeatMode = 1
        cardView.animation = translateAnimation
        translateAnimation.start()
    }

    private fun musicManager(){
        initMediaPlayer(itemList[index].music)
        music_name.text = itemList[index].title
        music_author.text = itemList[index].content
        picture.setImageResource(itemList[index].image)
        mediaParser.start()
        musicPlay.setImageResource(R.drawable.start)
    }



    inner class ItemAdapter(private val itemList: ArrayList<ItemClass>) :
        RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.itemTitle)
            val itTitle: TextView = view.findViewById(R.id.itemContent)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.dog_item, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.itemView.setOnClickListener {
                //获取位置
                index = viewHolder.adapterPosition
                //下标
                val list = itemList[index]
//                Toast.makeText(parent.context, "你点击了"+list.title, Toast.LENGTH_LONG).show()
                mediaParser.reset()
                initMediaPlayer(list.music)
                musicPlay.setImageResource(R.drawable.start)
                mediaParser.start()

                music_name.text = list.title
                music_author.text = list.content
                picture.setImageResource(list.image)

            }

            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = itemList[position]
            holder.title.text = item.title
            holder.itTitle.text = item.content
        }

        override fun getItemCount() = itemList.size
    }


    private fun initMediaPlayer(music: String) {
        val assetManager = assets
        val fd = assetManager.openFd(music)
        mediaParser.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
        mediaParser.prepare()
        zhuang(mediaParser.duration)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaParser.stop()
        mediaParser.release()
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private fun fullScreen(activity: Activity) {
        run {

            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            val window = activity.window
            val decorView = window.decorView
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }


}