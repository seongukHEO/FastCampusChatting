package kr.co.seonguk.application.fastcampuschatting.chatdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database
import kr.co.seonguk.application.fastcampuschatting.Key.Companion.DB_CHATS
import kr.co.seonguk.application.fastcampuschatting.Key.Companion.DB_CHAT_ROOMS
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
    private var myUserName: String = ""

    private val chatItemList = mutableListOf<ChatItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerview()
        settingChat()
        settingEvent()
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

                myUserName = myUserItem?.userName?:""
            }

        Firebase.database.reference.child(DB_USERS).child(otherUserId).get()
            .addOnSuccessListener {
                val otherUserItem = it.getValue(UserItem::class.java)

                chatAdapter.otherUserItem = otherUserItem
            }

        Firebase.database.reference.child(DB_CHATS).child(chatRoomId)
            .addChildEventListener(object : ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    //새로운 메시지가 생겼을 때
                    val chatItem = snapshot.getValue(ChatItem::class.java)
                    chatItem ?: return


                    chatItemList.add(chatItem)

                    chatAdapter.submitList(chatItemList.toMutableList())

                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    //이건 메시지 수정기능
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    //이건 메시지 삭제 기능
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


    }


    private fun settingEvent(){
        binding.apply {
            sendButton.setOnClickListener{
                val message = binding.messageEditText.text.toString()

                if(message.isEmpty()){
                    Toast.makeText(this@ChatActivity, "빈 메시지를 전송할 순 없습니다", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val newChatItem = ChatItem(
                    message = message,
                    userId = myUserId
                )

                Firebase.database.reference.child(DB_CHATS).child(chatRoomId).push().apply {
                    newChatItem.chatId = key
                    setValue(newChatItem)
                }

                val updates:MutableMap<String, Any> = hashMapOf(
                    "${DB_CHAT_ROOMS}/$myUserId/$otherUserId/lastMessage" to message,
                    "${DB_CHAT_ROOMS}/$myUserId/$myUserId/lastMessage" to message,
                    "${DB_CHAT_ROOMS}/$myUserId/$myUserId/chatRoomId" to chatRoomId,
                    "${DB_CHAT_ROOMS}/$myUserId/$myUserId/otherUserId" to myUserId,
                    "${DB_CHAT_ROOMS}/$myUserId/$myUserId/otherUserName" to myUserName,

                )

                Firebase.database.reference.updateChildren(updates)

                binding.messageEditText.text.clear()

            }
        }
    }
}