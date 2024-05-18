package kr.co.seonguk.application.fastcampuschatting.userList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kr.co.seonguk.application.fastcampuschatting.Key.Companion.DB_CHAT_ROOMS
import kr.co.seonguk.application.fastcampuschatting.Key.Companion.DB_USERS
import kr.co.seonguk.application.fastcampuschatting.R
import kr.co.seonguk.application.fastcampuschatting.chatList.ChatRoomItem
import kr.co.seonguk.application.fastcampuschatting.chatdetail.ChatActivity
import kr.co.seonguk.application.fastcampuschatting.databinding.FragmentUserBinding
import java.util.UUID

class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var binding: FragmentUserBinding

    val userListAdapter = UserAdapter{otherUser ->
        val myUserId = Firebase.auth.currentUser?.uid ?: ""
        val chatRoomDB = Firebase.database.reference.child(DB_CHAT_ROOMS).child(myUserId).child(otherUser.userId?:"")

        //해당 채팅방의 정보를 가져온다?
        chatRoomDB.get().addOnSuccessListener {

            var chatRoomId = ""

            if (it.value != null){
                //데이터가 존재한다
                val chatRoom = it.getValue(ChatRoomItem::class.java)
                chatRoomId = chatRoom?.chatRoomId?:""

            }else{
                //데이터 미존재

                //랜덤 아이디를 뽑아준다
                chatRoomId = UUID.randomUUID().toString()

                //새로운 채팅방 만들기
                val newChatRoom = ChatRoomItem(
                    chatRoomId = chatRoomId,
                    otherUserName = otherUser.userName,
                    otherUserId = otherUser.userId
                )

                //채팅룸 데이터 베이스에 새로운 채팅룸 정보 넣어주기
                chatRoomDB.setValue(newChatRoom)
            }

            val newIntent = Intent(context, ChatActivity::class.java)
            newIntent.putExtra("otherUserId", otherUser.userId)
            newIntent.putExtra("chatRoomId", chatRoomId)
            startActivity(newIntent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUser()

        binding = FragmentUserBinding.bind(view)
        settingRecyclerview()

    }

    //리사이클러뷰
    private fun settingRecyclerview(){
        binding.userListRecyclerview.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    //사용자의 정보를 조회한다
    private fun checkUser(){
        Firebase.database.reference.child(DB_USERS).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUserId = Firebase.auth.currentUser?.uid ?: ""
                val userItemList = mutableListOf<UserItem>()

                //친구 리스트에 나는 필요 없기 때문에 빼주는 부분이다
                snapshot.children.forEach {
                    val user = it.getValue(UserItem::class.java) ?: return

                    if(user.userId != currentUserId){
                        userItemList.add(user)
                    }
                }

                userListAdapter.submitList(userItemList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("test1234", "${error}")
            }
        })
    }
}