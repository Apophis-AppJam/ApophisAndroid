package com.example.apophis_android.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.apophis_android.ui.MainActivity
import com.example.apophis_android.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.ApiErrorCode
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created By chosungrim
 * on 01월 09일, 2020
 */

class LoginActivity : AppCompatActivity() {

    //카카오 로그인 콜백 선언
    private var sessionCallback: SessionCallback? = null

    // Google Sign-In Methods
    var googleSignInClient : GoogleSignInClient? = null
    val RC_SIGN_IN = 1000

    companion object {
        const val RESULT_CODE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionCallback = SessionCallback()
        Session.getCurrentSession().addCallback(sessionCallback)
        Session.getCurrentSession().checkAndImplicitOpen()

        login_kakao_btn.setOnClickListener { Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, this@LoginActivity) }

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        login_google_btn.setOnClickListener {
            var signInIntent = googleSignInClient?.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }


    // Move to MainActivity
    fun MoveNextPage() {
        var currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }

    // Google Sign-In Methods
    fun FirebaseAuthWithGoogle(acct : GoogleSignInAccount?) {
        var credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful){
                MoveNextPage()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        MoveNextPage() // Google Automatic Login
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)

        // Google Sign-In Methods
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                FirebaseAuthWithGoogle(account)
            }
            catch (e : ApiException){

            }
        } else {

        }
    }

    override fun onDestroy() {
        //카카오 로그인 콜백 제거
        super.onDestroy()
        Session.getCurrentSession().removeCallback(sessionCallback)
    }

    private inner class SessionCallback : ISessionCallback {
        override fun onSessionOpened() { //세션이 성공적으로 열린 경우
            UserManagement.getInstance().me(object : MeV2ResponseCallback() {
                //유저 정보를 가져온다.
                override fun onFailure(errorResult: ErrorResult) { //유저 정보를 가져오는 데 실패한 경우
                    val result: Int = errorResult.getErrorCode() //오류 코드를 받아온다.
                    if (result == ApiErrorCode.CLIENT_ERROR_CODE) { //클라이언트 에러인 경우: 네트워크 오류
                        Toast.makeText(applicationContext, "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                        finish()
                    } else { //클라이언트 에러가 아닌 경우: 기타 오류
                        Toast.makeText(applicationContext, "로그인 도중 오류가 발생했습니다: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onSuccess(result: MeV2Response?) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onSessionClosed(errorResult: ErrorResult) { //세션이 도중에 닫힌 경우
                    Toast.makeText(applicationContext, "세션이 닫혔습니다. 다시 시도해 주세요: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show()
                }
            })
        }

        override fun onSessionOpenFailed(e: KakaoException) { //세션을 여는 도중 오류가 발생한 경우 -> Toast 메세지를 띄움.
            Toast.makeText(applicationContext, "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: " + e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}