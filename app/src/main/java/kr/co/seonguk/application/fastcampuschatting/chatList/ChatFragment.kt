package kr.co.seonguk.application.fastcampuschatting.chatList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kr.co.seonguk.application.fastcampuschatting.Key.Companion.DB_CHAT_ROOMS
import kr.co.seonguk.application.fastcampuschatting.R
import kr.co.seonguk.application.fastcampuschatting.databinding.FragmentChatlistBinding
import kr.co.seonguk.application.fastcampuschatting.databinding.FragmentUserBinding

class ChatFragment : Fragment(R.layout.fragment_chatlist) {

    private lateinit var binding: FragmentChatlistBinding

    val chatListAdapter = ChatListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatlistBinding.bind(view)
        settingRecyclerview()
        gettingChat()

    }

    //리사이클러뷰
    private fun settingRecyclerview(){
        binding.chatListRecyclerview.apply {
            adapter = chatListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    //파이어 베이스에서 채팅 리스트를 가져온다
    private fun gettingChat(){

        //사용자의 아이디를 가져온다
        //근데 이거 SharedPreference를 사용하면 더 편리할 듯..?
        val currentUserId = Firebase.auth.currentUser?.uid ?:""

        val chatRoomsDB = Firebase.database.reference.child(DB_CHAT_ROOMS).child(currentUserId)

        //채팅의 경우 업데이트가 될 때마다 값을 가져와야 하기 때문에
        //addValueEventListener()를 사용한다
        chatRoomsDB.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatRoomList = snapshot.children.map {
                    it.getValue(ChatRoomItem::class.java)

                }

                chatListAdapter.submitList(chatRoomList)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}