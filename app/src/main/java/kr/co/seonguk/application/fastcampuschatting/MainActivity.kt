package kr.co.seonguk.application.fastcampuschatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kr.co.seonguk.application.fastcampuschatting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkUser()
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
}