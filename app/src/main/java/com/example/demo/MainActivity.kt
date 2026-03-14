package com.example.demo

import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // Handler 用于在不同线程之间传递消息
    // Handler 关联着主线程的 Looper，所以 postRunnable 会在主线程执行
    private val handler = Handler(Looper.getMainLooper())

    // 用于显示倒计时的 TextView
    private lateinit var textView: TextView

    // 倒计时数字
    private var count = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        textView.text = "倒计时: $count"

        // 使用 Handler.postDelayed 实现延迟执行
        // 这会创建一个 Runnable，在指定时间后执行
        handler.postDelayed(object : Runnable {
            override fun run() {
                count--
                textView.text = "倒计时: $count"
                if (count > 0) {
                    // 继续延迟执行，实现循环
                    handler.postDelayed(this, 1000)
                } else {
                    textView.text = "倒计时结束!"
                }
            }
        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 页面销毁时移除所有回调，防止内存泄漏
        handler.removeCallbacksAndMessages(null)
    }
}
