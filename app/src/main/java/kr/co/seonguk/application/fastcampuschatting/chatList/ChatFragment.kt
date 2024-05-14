package kr.co.seonguk.application.fastcampuschatting.chatList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.seonguk.application.fastcampuschatting.R
import kr.co.seonguk.application.fastcampuschatting.databinding.FragmentChatlistBinding
import kr.co.seonguk.application.fastcampuschatting.databinding.FragmentUserBinding

class ChatFragment : Fragment(R.layout.fragment_chatlist) {

    private lateinit var binding: FragmentChatlistBinding

    val chatListAdapter = ChatListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        test()

        binding = FragmentChatlistBinding.bind(view)
        settingRecyclerview()

    }

    //리사이클러뷰
    private fun settingRecyclerview(){
        binding.chatListRecyclerview.apply {
            adapter = chatListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    //테스트로 값을 넣어보자
    private fun test(){
        chatListAdapter.submitList(
            mutableListOf<ChatRoomItem>().apply {
                add(ChatRoomItem("11", "22", "하하"))
            }
        )
    }
}