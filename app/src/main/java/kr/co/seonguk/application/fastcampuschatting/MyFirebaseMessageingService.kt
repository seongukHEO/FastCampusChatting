package kr.co.seonguk.application.fastcampuschatting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessageingService: FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val name = "채팅 알림"
        val descriptionText = "채팅 알림"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(getString(R.string.default_notification_channel_id), name, importance)
        mChannel.description = descriptionText
        // Register the channel with the system. You can't change the importance
        // or other notification behaviors after this.
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //채널을 만든다
        notificationManager.createNotificationChannel(mChannel)

        //메시지에서 데이터를 가져온다
        val body = message.notification?.body ?:""
        val notificationBuilder = NotificationCompat.Builder(applicationContext, getString(R.string.default_notification_channel_id))
            //알림이 오면 밑의 아이콘을 보여주겠다
            .setSmallIcon(R.drawable.baseline_chat_24)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(body)

        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

}