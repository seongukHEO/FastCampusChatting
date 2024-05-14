package kr.co.seonguk.application.fastcampuschatting.userList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.seonguk.application.fastcampuschatting.R
import kr.co.seonguk.application.fastcampuschatting.databinding.FragmentUserBinding

class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var binding: FragmentUserBinding

    val userListAdapter = UserAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        test()

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

    //테스트로 값을 넣어보자
    private fun test(){
        userListAdapter.submitList(
            mutableListOf<UserItem>().apply {
                add(UserItem("11", "22", "하하"))
            }
        )
    }
}