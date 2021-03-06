package com.android.mvvmdatabind2.mvvm.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.android.mvvmdatabind2.activities.MainActivity
import com.android.mvvmdatabind2.activities.auth.LoginActivity
import com.android.mvvmdatabind2.others.Constants.USERS
import com.android.mvvmdatabind2.others.Constants.USER_EMAIL
import com.android.mvvmdatabind2.others.Constants.USER_ID
import com.android.mvvmdatabind2.others.Constants.USER_NAME
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AuthRepository(private var context: Context) : BaseRepository(context) {
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference(USERS)
    private var mAuth = FirebaseAuth.getInstance()


    fun login(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (mAuth.currentUser!!.isEmailVerified) {
                            Toast.makeText(context, "Signed In as $email", Toast.LENGTH_SHORT).show()
                            Intent(context, MainActivity::class.java).also {
                                it.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                context.startActivity(it)
                            }
                        } else {
                            Toast.makeText(context, "First Verify Your Email", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Log.d(TAG, "login: Login Failed :- ${task.exception}")
                    }
                }
        } else {
            Toast.makeText(context, "Fill The Fields", Toast.LENGTH_SHORT).show()
        }

    }


    fun forgotPassword(email: String) {
        if (email.isNotEmpty())
        {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener {

            }
        }
    }







    fun register(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
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
                            if (user != null) {
                                myRef.child(user.uid).child(USER_EMAIL).setValue(email)
                                myRef.child(user.uid).child(USER_NAME).setValue(
                                    user.displayName ?: " "
                                )
                                myRef.child(user.uid).child(USER_ID).setValue(user.uid)
                            }
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
        } else {
            Toast.makeText(context, "Fill The Fields", Toast.LENGTH_SHORT).show()
        }

    }
}





