package kr.co.seonguk.application.fastcampuschatting.chatdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import kr.co.seonguk.application.fastcampuschatting.Key.Companion.DB_USERS
import kr.co.seonguk.application.fastcampuschatting.R
import kr.co.seonguk.application.fastcampuschatting.databinding.ActivityChatBinding
import kr.co.seonguk.application.fastcampuschatting.userList.UserItem

class ChatActivity : AppCompatActivity() {

    lateinit var binding:ActivityChatBinding

    val chatAdapter = ChatAdapter()

    private var chatRoomId : String = ""
    private var otherUserId : String = ""
    private var myUserId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerview()
        settingChat()
    }


    private fun setRecyclerview(){
        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }
    }

    private fun settingChat(){
        chatRoomId = intent.getStringExtra("chatRoomId") ?: return
        otherUserId = intent.getStringExtra("otherUserId") ?: return
        myUserId = Firebase.auth.currentUser?.uid?:""

        Firebase.database.reference.child(DB_USERS).child(myUserId).get()
            .addOnSuccessListener {
                val myUserItem = it.getValue(UserItem::class.java)
            }

        Firebase.database.reference.child(DB_USERS).child(otherUserId).get()
            .addOnSuccessListener {
                val otherUserItem = it.getValue(UserItem::class.java)
            }



    }
}