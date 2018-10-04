package com.yooncheol.yoonracket.util

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseUtils {
    companion object {
        fun getProductRef() : DatabaseReference = FirebaseDatabase.getInstance().getReference("products")

        fun getUserRef() : DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

        fun getCommentRef() : DatabaseReference = FirebaseDatabase.getInstance().getReference("comments")
    }

}