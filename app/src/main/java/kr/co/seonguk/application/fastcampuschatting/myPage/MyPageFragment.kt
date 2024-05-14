package kr.co.seonguk.application.fastcampuschatting.myPage

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kr.co.seonguk.application.fastcampuschatting.LoginActivity
import kr.co.seonguk.application.fastcampuschatting.R
import kr.co.seonguk.application.fastcampuschatting.Util
import kr.co.seonguk.application.fastcampuschatting.databinding.FragmentMyPageBinding


class MyPageFragment : Fragment(R.layout.fragment_my_page) {

    lateinit var binding:FragmentMyPageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPageBinding.bind(view)
        settingEvent()

    }


    private fun settingEvent(){
        binding.apply {
            applyButton.setOnClickListener {
                val userName = binding.userNameEditText.text.toString()
                val description = binding.descriptionEditText.text.toString()

                if (userName.isNullOrEmpty()){
                    Util.showDiaLog(requireContext(), "유저이름 오류", "유저 이름을 입력해주세요"){ dialogInterface: DialogInterface, i: Int ->

                    }
                }
            }

            signOutButton.setOnClickListener {
                Firebase.auth.signOut()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                activity?.finish()
            }
        }
    }

}