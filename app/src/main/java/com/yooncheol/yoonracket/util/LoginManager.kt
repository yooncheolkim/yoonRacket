package com.yooncheol.yoonracket.util

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yooncheol.yoonracket.data.User
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import java.util.concurrent.TimeUnit

object LoginManager {
    val TAG : String = "Loginmanager"

    fun isLoggedIn() : Single<Boolean>{
        Log.d(TAG,"isLoggedIn : ")

        var result : SingleSubject<Boolean> = SingleSubject.create()

        var currentUser : FirebaseUser ? = FirebaseAuth.getInstance()
                .currentUser

        result.onSuccess(currentUser != null)
        return result.delay(1,TimeUnit.SECONDS)
    }

    fun singOut(){
        Log.d(TAG, "sing out : ")
        FirebaseAuth.getInstance().signOut()
    }

    fun getUser() : User {
        var currentUser: FirebaseUser? = FirebaseAuth.getInstance()
                .currentUser
        return object : User(currentUser?.displayName, currentUser?.photoUrl.toString()){}
    }
}