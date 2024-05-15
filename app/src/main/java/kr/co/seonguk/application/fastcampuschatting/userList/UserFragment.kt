package kr.co.seonguk.application.fastcampuschatting.userList

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
import kr.co.seonguk.application.fastcampuschatting.Key.Companion.DB_USERS
import kr.co.seonguk.application.fastcampuschatting.R
import kr.co.seonguk.application.fastcampuschatting.databinding.FragmentUserBinding

class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var binding: FragmentUserBinding

    val userListAdapter = UserAdapter()

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