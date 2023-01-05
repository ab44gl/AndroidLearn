package com.abhishek.androidlearn

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout.LayoutParams
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.abhishek.androidlearn.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//abhijeetkr531@gmail.com
//br31ag0193abhi

class MainActivity : AppCompatActivity() {
    var lorem: Lorem = LoremIpsum.getInstance()
    private lateinit var binding: ActivityMainBinding
    private lateinit var driveHelper: DriveHelper
    private val signInResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                handleSignInResult(it.data)
            }
        }

    private fun handleSignInResult(data: Intent?) {
        GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnSuccessListener {
                Help.log_("Signed in as " + it.email)
                // Use the authenticated account to sign in to the Drive service.
                it.account?.let { it1 -> initDriveHelper(it1) }
            }
            .addOnFailureListener {
                Log.e(Help.TAG, "unable to sign in", it)
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //-------------------------------------
        requestSignIn()
        //
        binding.layout.apply {
            fun create(name: String, f: () -> Unit) {
                val button = Button(this@MainActivity).apply {
                    text = name
                    setOnClickListener {
                        lifecycleScope.launch(Dispatchers.IO) {
                            f()
                        }

                    }
                }
                addView(
                    button, LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT
                    )
                )
            }
            //drive fun
            create("create") {
                driveHelper.createTextFile(data = lorem.getParagraphs(3, 5))
            }
            create("deleteAll") {
                driveHelper.deleteAll()
            }
            create("list") {
                val res = driveHelper.list()
            }
            create("create folder") {
                driveHelper.createFolder()
            }
            create("read") {
                driveHelper.readFile("1bx74kGMm5gWroJmMb4ykW794sN38Ke2y");
            }
            create("update") {
                driveHelper.updateFile("1bx74kGMm5gWroJmMb4ykW794sN38Ke2y", data = "nothing");
            }

        }
    }

    private fun demoDeleteAll() {
        lifecycleScope.launch(Dispatchers.IO) {
            driveHelper.deleteAll()
        }
    }

    private fun demoListFile() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = driveHelper.list()

        }
    }

    private fun demoCreateFile() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = driveHelper.createFile()

        }
    }

    override fun onStart() {
        super.onStart()

    }

    private fun requestSignIn() {
        val lastSignIn = GoogleSignIn.getLastSignedInAccount(this)
        if (lastSignIn == null) {
            val signInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestScopes(Scope(DriveScopes.DRIVE_FILE))
                    .build()
            val client = GoogleSignIn.getClient(this, signInOptions)
            signInResultLauncher.launch(client.signInIntent)
        } else {
            lastSignIn.account?.let { initDriveHelper(it) }
        }
    }

    private fun initDriveHelper(account: Account) {
        val credential = GoogleAccountCredential.usingOAuth2(
            this, arrayListOf(DriveScopes.DRIVE_FILE)
        )
        credential.selectedAccount = account
        val googleDriveService = Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            GsonFactory(),
            credential
        )
            .setApplicationName(resources.getString(R.string.app_name))
            .build()
        driveHelper = DriveHelper(googleDriveService)
    }
}