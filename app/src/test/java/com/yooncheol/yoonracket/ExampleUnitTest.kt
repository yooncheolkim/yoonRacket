package com.yooncheol.yoonracket

import android.renderscript.Sampler
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.yooncheol.yoonracket.util.FirebaseDatabaseUtils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        FirebaseDatabaseUtils.getProductRef()
                .orderByChild("name").equalTo("astrox 9")
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot) {
                        println("onDataChange() : ")
                        print(p0)
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        println("onCancelled() : ")
                    }
                })

        assertEquals(4, 2 + 2)
    }
}
