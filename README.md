# android-handler-demo

## 简介

本 demo 展示 Android 中 Handler 的基本用法，通过一个简单的倒计时示例演示如何在主线程中执行延迟任务。

## 基本原理

Handler 是 Android 提供的消息处理机制，主要用于：

1. **线程间通信**：在不同线程之间传递消息和 Runnable
2. **延迟执行**：在指定时间后执行代码
3. **主线程更新**：从后台线程向主线程发送消息，更新 UI

Handler 关联一个 Looper，每个线程只能有一个 Looper。Handler 通过 Looper 获取消息队列，并发送消息到队列中。Looper 会不断从队列中取出消息，交给 Handler 处理。

## 启动和使用

### 环境要求
- Android Studio 3.0+
- JDK 1.8+
- Android SDK 28

### 安装和运行
1. 用 Android Studio 打开此项目
2. 连接 Android 设备或启动模拟器
3. 点击 Run 运行项目

## 教程

### 什么是 Handler？

Handler 是 Android 系统中线程间通信的核心机制。在 Android 中，主线程（UI 线程）负责处理 UI 更新，而耗时的操作应该在后台线程中执行。Handler 就是在后台线程和主线程之间传递消息的桥梁。

### Handler 的基本用法

创建 Handler 时需要指定关联的 Looper。通常我们使用主线程的 Looper：

```kotlin
val handler = Handler(Looper.getMainLooper())
```

### 延迟执行任务

使用 `postDelayed` 方法可以延迟执行一个 Runnable：

```kotlin
handler.postDelayed(object : Runnable {
    override fun run() {
        // 这里会在指定时间后执行
        textView.text = "倒计时: $count"
    }
}, 1000)  // 延迟 1000 毫秒
```

### 循环执行

在 Runnable 内部再次调用 `postDelayed`，可以实现循环执行的效果：

```kotlin
handler.postDelayed(object : Runnable {
    override fun run() {
        count--
        if (count > 0) {
            handler.postDelayed(this, 1000)  // 递归调用，继续延迟
        }
    }
}, 1000)
```

### 内存泄漏的预防

如果 Activity 销毁时 Handler 还在执行延迟任务，会导致内存泄漏。解决方法：

1. 在 onDestroy 中移除所有回调：`handler.removeCallbacksAndMessages(null)`
2. 使用弱引用（WeakReference）
3. 使用 lifecycle-aware 组件

### 注意事项

1. Handler 关联的 Looper 决定了任务在哪个线程执行
2. 不在主线程创建 Handler 时，需要先调用 `Looper.prepare()` 和 `Looper.loop()`
3. 移除回调要在合适的时机调用，避免空指针异常
