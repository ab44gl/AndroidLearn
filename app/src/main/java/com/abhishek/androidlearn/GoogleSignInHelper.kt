package com.abhishek.androidlearn

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleSignInHelper(private val activity: AppCompatActivity,private  val signInListener: ((GoogleSignInAccount?) -> Unit) ) {
    companion object{
        fun accountString(account: GoogleSignInAccount):String {
            var msg=""
            msg+="displayName:${account.displayName}\n"
            msg+="photoUrl:${account.photoUrl}\n"
            msg+="type:${account.account?.type}\n"
            msg+="id:${account.id}\n"
            msg+="email:${account.email}\n"
            msg+="scopes:${account.grantedScopes}\n"
            return msg
        }
    }
    private val signInResultLauncher=activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== AppCompatActivity.RESULT_OK){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            signInListener(account)
        } catch (e: ApiException) {
            Help.log_(e.statusCode)
            signInListener(null)
        }
    }
    fun isAlreadySignIn():Boolean {
        return GoogleSignIn.getLastSignedInAccount(activity)!=null
    }

    fun signIn() {
        val account = GoogleSignIn.getLastSignedInAccount(activity)
        if(account!=null){
            signInListener(account)
        }else{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            // Build a GoogleSignInClient with the options specified by gso.
            val signInClient = GoogleSignIn.getClient(activity, gso)
            signInClient.let {
                signInResultLauncher.launch(it.signInIntent)
            }
        }

    }
}