package kr.co.seonguk.application.fastcampuschatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kr.co.seonguk.application.fastcampuschatting.chatList.ChatFragment
import kr.co.seonguk.application.fastcampuschatting.databinding.ActivityMainBinding
import kr.co.seonguk.application.fastcampuschatting.myPage.MyPageFragment
import kr.co.seonguk.application.fastcampuschatting.userList.UserFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    private val userFragment = UserFragment()
    private val chatFragment = ChatFragment()
    private val myPageFragment = MyPageFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkUser()
        settingEvent()
        replaceFragment(userFragment)
    }


    //파이어 베이스에 유효한 사용자가 있는지 확인한다
    private fun checkUser(){
        //사용자가 있는지 확인하는 변수
        val currentUser = Firebase.auth.currentUser

        //사용자가 없을 때
        if (currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun replaceFragment(fragment: Fragment){

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()

    }

    private fun settingEvent(){
        binding.apply {
            bottomNavigationView.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.userList -> {
                        replaceFragment(userFragment)
                        return@setOnItemSelectedListener true
                    }
                    R.id.chatRoomList -> {
                        replaceFragment(chatFragment)
                        return@setOnItemSelectedListener true
                    }
                    R.id.myPageList -> {
                        replaceFragment(myPageFragment)
                        return@setOnItemSelectedListener true
                    }
                    else -> {

                        return@setOnItemSelectedListener false
                    }

                }
            }
        }
    }
}