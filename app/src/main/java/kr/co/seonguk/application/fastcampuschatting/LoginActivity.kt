package kr.co.seonguk.application.fastcampuschatting

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kr.co.seonguk.application.fastcampuschatting.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getLogin()
    }


    //로그인 처리
    private fun getLogin(){
        binding.apply {
            //회원가입
            signUpButton.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                if (email.isEmpty()){
                    Util.showDiaLog(this@LoginActivity, "이메일 오류", "이메일을 입력해주세요"){ dialogInterface: DialogInterface, i: Int ->
                        Util.showSoftInput(emailEditText, this@LoginActivity)
                    }
                }else if (password.isEmpty()){
                    Util.showDiaLog(this@LoginActivity, "비밀번호 오류", "비밀번호를 입력해주세요"){ dialogInterface: DialogInterface, i: Int ->
                        Util.showSoftInput(passwordEditText, this@LoginActivity)
                    }
                }else{
                    Firebase.auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                //회원가입 성공
                                Toast.makeText(this@LoginActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()

                            }else{
                                //회원가입 실패
                                Log.e("test1234", "${task}")
                                Util.showDiaLog(this@LoginActivity, "회원가입 실패", "회원가입에 실패하였습니다"){ dialogInterface: DialogInterface, i: Int ->

                                }

                            }
                        }
                }
            }

            //로그인
            signInButton.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                if (email.isEmpty()){
                    Util.showDiaLog(this@LoginActivity, "이메일 오류", "이메일을 입력해주세요"){ dialogInterface: DialogInterface, i: Int ->
                        Util.showSoftInput(emailEditText, this@LoginActivity)
                    }
                }else if (password.isEmpty()){
                    Util.showDiaLog(this@LoginActivity, "비밀번호 오류", "비밀번호를 입력해주세요"){ dialogInterface: DialogInterface, i: Int ->
                        Util.showSoftInput(passwordEditText, this@LoginActivity)
                    }
                }else{
                    Firebase.auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                //로그인 성공
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            }else{
                                //로그인 실패
                                Util.showDiaLog(this@LoginActivity, "로그인 실패", "로그인에 실패하였습니다"){ dialogInterface: DialogInterface, i: Int ->

                                }

                            }
                        }
                }
            }

        }
    }
}