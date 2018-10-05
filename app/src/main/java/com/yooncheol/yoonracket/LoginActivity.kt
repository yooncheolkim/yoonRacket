package com.yooncheol.yoonracket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.yooncheol.yoonracket.util.LoginManager
import com.yooncheol.yoonracket.view.main.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    val TAG : String = "LoginActivity"
    val RC_SIGN_IN = 101
    private val mGoogleLoginButtion by lazy{
        findViewById(R.id.googleLoginBnt) as Button
    }

    private var mDisposables : CompositeDisposable = CompositeDisposable()

    lateinit var mGoogleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initGoogleLogin()
        checkLoginStatus()
    }

    fun initGoogleLogin(){
        Log.d(TAG,"init google login : ")
        var gso : GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        mGoogleLoginButtion.setOnClickListener{
            var signInIntent : Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent,RC_SIGN_IN)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            var task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
    }

    fun handleGoogleSignInResult(completedTask : Task<GoogleSignInAccount> ){
        Log.d(TAG,"handleGoogleSignInResult : ")

        try{
            var account : GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            //singed in succesfully, show authenticated UI
            onGoogleLoginSuccess(account)
        }catch (e : ApiException){
            Log.w(TAG,"singInResult:failed cod = " + e.statusCode)
            onLoginFailure()
        }
    }

    fun onGoogleLoginSuccess(account : GoogleSignInAccount){
        Log.d(TAG, "firebaseAuthWithGoogle : " + account.getId())

        var credential : AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener {
                    if(it.isSuccessful()){
                        Log.d(TAG,"signInWithCredential:success")
                        onLoginSuccess()
                    }else{
                        Log.w(TAG,"signInWithCredential : failure", it.exception)
                        onLoginFailure()
                    }
                }
    }

    fun onLoginSuccess(){
        Log.d(TAG,"onLoginSuccess :")
        gotoMainActivity()
    }

    fun onLoginFailure(){
        Log.d(TAG,"onLoginFailure : ")
        Toast.makeText(this,"Login Failure",Toast.LENGTH_SHORT).show()
    }


    fun gotoMainActivity(){
        Log.d(TAG,"gotoMainActivity : ")

        var intent = Intent(this,MainActivity::class.java)
        ContextCompat.startActivity(this,intent, null)
        finish()
    }


    fun checkLoginStatus(){
        Log.d(TAG ,"checkLoginStatus")

        mDisposables.add(
                LoginManager.isLoggedIn()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { isLoggedIn : Boolean ->
                            if(isLoggedIn)gotoMainActivity() else showLoginButtons()
                        }
        )
    }

    fun showLoginButtons(){
        Log.d(TAG,"showLoginButtons : ")
        mGoogleLoginButtion.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy() : ")
        mDisposables.dispose()
    }
}
