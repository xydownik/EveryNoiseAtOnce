package com.example.everynoiseatonce.utils

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserProvider @Inject constructor() {
    fun getUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid ?: "guest"
    }
}
