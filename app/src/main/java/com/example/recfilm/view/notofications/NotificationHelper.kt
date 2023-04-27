package com.example.recfilm.view.notofications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.recfilm.R
import com.example.recfilm.data.Entity.Film
import com.example.recfilm.view.MainActivity
import com.example.remote_module.entity.ApiConstants

object NotificationHelper {
    fun createNotification(context: Context, film: Film) {

        //Интент для перехода в приложение
        val mIntent = Intent(context, MainActivity::class.java)

        // pendingIntent для запуска активити из другой части приложения
        val pendingIntent =
            PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        //Создаём нотификацию
        val builder =
            NotificationCompat.Builder(context!!, NotificationConstants.CHANNEL_ID).apply {
                setSmallIcon(R.drawable.ic_outline_watch_later_24)
                setContentTitle("Не забудьте посмотреть!")
                setContentText(film.title)
                priority = NotificationCompat.PRIORITY_DEFAULT
                setContentIntent(pendingIntent)
                setAutoCancel(true)
            }
        //Получам менеджер нотификации
        val notificationManager = NotificationManagerCompat.from(context)

        //Добавим картинку нотификации
        Glide.with(context)
            //говорим, что нужен битмап
            .asBitmap()
            //указываем, откуда загружать, это ссылка, как на загрузку с API
            .load(ApiConstants.IMAGES_URL + "w500" + film.poster)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }
                //Этот коллбэк отрабатывает, когда мы успешно получим битмап
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    //Создаем нотификации в стиле big picture
                    builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    //Обновляем нотификацию
                    notificationManager.notify(film.id, builder.build())
                }
            })
        //Отправляем изначальную нотификацию в стандартном исполнении
        notificationManager.notify(film.id, builder.build())
    }
}