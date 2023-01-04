package com.abhishek.androidlearn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.abhishek.androidlearn.databinding.ActivityGdriveBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import com.google.api.services.drive.model.FileList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GDriveActivity : AppCompatActivity() {
    private  lateinit var binding:ActivityGdriveBinding
    private lateinit var googleSignInAccount: GoogleSignInAccount
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       binding=ActivityGdriveBinding.inflate(layoutInflater)
       setContentView(binding.root)
       //-------------------------------------
        googleSignInAccount= GoogleSignInHelper.getLastSignInAccount(this)!!

        binding.apply {
            butAllFiles.setOnClickListener{
                allFiles()
            }
        }

    }
    private fun getDrive(account: GoogleSignInAccount):Drive{
        val credential = GoogleAccountCredential.usingOAuth2(
            this, listOf(DriveScopes.DRIVE_FILE)
        )
        credential.selectedAccount = account.account
        return  Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            JacksonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName(getString(R.string.app_name))
            .build()
    }
    private fun allFiles() {
        val drive=getDrive(googleSignInAccount)

        lifecycleScope.launch(Dispatchers.IO){
            try {
                val result: FileList = drive.files().list()
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name)")
                    .execute()
                val files: List<File> = result.files
                Help.log_(files.size)
            }catch (e:UserRecoverableAuthIOException){
                Help.log_(e.intent)
                startActivity(e.intent)
            }
        }
//        lifecycleScope.launch(Dispatchers.IO){
//            var pageToken: String? = null
//            var info=""
//            do {
//                val result = drive.files().list().apply(){
//                spaces = "drive"
//                fields = "nextPageToken, files(id, name)"
//                pageToken = this.pageToken
//            }.execute()
//            for (file in result.files) {
//                 info+=("name=${file.name} id=${file.id}\n")
//            }
//        } while (pageToken != null)
//        Help.log_(info)
//        }
    }

}