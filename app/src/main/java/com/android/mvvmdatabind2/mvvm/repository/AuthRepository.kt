package com.android.mvvmdatabind2.mvvm.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.android.mvvmdatabind2.activities.MainActivity
import com.android.mvvmdatabind2.activities.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class AuthRepository(private var context: Context)  : BaseRepository(context){
    private var mAuth = FirebaseAuth.getInstance()
    fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (mAuth.currentUser!!.isEmailVerified) {
                            val user = mAuth.currentUser
                            Intent(context, MainActivity::class.java).also {
                                it.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                context.startActivity(it)
                            }
                        } else {
                            val user2 = mAuth.currentUser
                            CoroutineScope(IO).launch {
                                if (user2 != null) {
                                }
                            }
                            Toast.makeText(context, "First Verify Your Email", Toast.LENGTH_SHORT)
                                .show()
                        }
                } else {
                    Log.d(TAG, "login: Login Failed :- ${task.exception}")
                }
            }
    }


    fun forgotPassword(email: String, password: String) {
        mAuth.confirmPasswordReset(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "forgotPassword: Password Has Been Reset")
            } else {
                Log.d(TAG, "forgotPassword: ${it.exception?.message}")
            }
        }
    }


    fun register(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                        Toast.makeText(
                            context,
                            "Check your Email For Verification",
                            Toast.LENGTH_SHORT
                        ).show()
                        val user = mAuth.currentUser
                        Intent(context, LoginActivity::class.java).also {
                            context.startActivity(it)
                        }
                    }

                } else {
                    Intent(context, LoginActivity::class.java).also {
                        context.startActivity(it)
                    }
                    Log.d(TAG, "login: Login Failed :- ${task.exception}")
                }
            }
    }





}