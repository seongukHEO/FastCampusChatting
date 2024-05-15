package kr.co.seonguk.application.fastcampuschatting.myPage

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import kr.co.seonguk.application.fastcampuschatting.Key.Companion.DB_USERS
import kr.co.seonguk.application.fastcampuschatting.LoginActivity
import kr.co.seonguk.application.fastcampuschatting.R
import kr.co.seonguk.application.fastcampuschatting.Util
import kr.co.seonguk.application.fastcampuschatting.databinding.FragmentMyPageBinding
import kr.co.seonguk.application.fastcampuschatting.userList.UserItem


class MyPageFragment : Fragment(R.layout.fragment_my_page) {

    lateinit var binding:FragmentMyPageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPageBinding.bind(view)
        settingEvent()
        gettingUserData()

    }


    private fun settingEvent(){
        binding.apply {
            applyButton.setOnClickListener {
                val userName = binding.userNameEditText.text.toString()
                val description = binding.descriptionEditText.text.toString()

                if (userName.isNullOrEmpty()){
                    Util.showDiaLog(requireContext(), "유저이름 오류", "유저 이름을 입력해주세요"){ dialogInterface: DialogInterface, i: Int ->

                    }
                }else{
                    changeUserData()
                }
            }

            signOutButton.setOnClickListener {
                Firebase.auth.signOut()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                activity?.finish()
            }
        }
    }


    //데이터를 변경한다
    private fun changeUserData(){

        val userName = binding.userNameEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()

        //또 이거 가져오지
        val currentUserId = Firebase.auth.currentUser?.uid?:""

        val myUserDB = Firebase.database.reference.child(DB_USERS).child(currentUserId)

        //map을 임의로 만들어서 값을 넣어준다
        val user = mutableMapOf<String, Any>()
        user["userName"] = userName
        user["description"] = description

        //이후 Add 해주면 값이 잘 들어간다
        myUserDB.updateChildren(user)

    }


    //데이터를 가져와서 보여준다
    private fun gettingUserData(){
        val currentUserId = Firebase.auth.currentUser?.uid?:""
        val myUserDB = Firebase.database.reference.child(DB_USERS).child(currentUserId)

        myUserDB.get().addOnSuccessListener {
            val currentUserItem = it.getValue(UserItem::class.java) ?: return@addOnSuccessListener

            binding.userNameEditText.setText(currentUserItem.userName)
            binding.descriptionEditText.setText(currentUserItem.description)
        }
    }

}