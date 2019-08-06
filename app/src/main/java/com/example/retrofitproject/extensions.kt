package com.example.retrofitproject

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast

fun Context.getToken(): String? {
    return "token " +getSharedPreferences("token", Context.MODE_PRIVATE)
        .getString("token", "")
}

fun Context.processResponseCode(code: Int){
    Toast.makeText(this, code.toString(), Toast.LENGTH_LONG).show()
    when(code) {
        401 -> getAuthorizeCode().apply {
            Toast.makeText(this@processResponseCode, "Failed to logIn", Toast.LENGTH_LONG).show()
        }
    }
}

fun Uri.downloadFromUri(context: Context, name: String, path: String) {
    val rm = DownloadManager.Request(this)
    rm.apply {
        addRequestHeader("Authorization", context.getToken())
        setTitle("Downloading $name")
        setVisibleInDownloadsUi(true)
        setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/GithubSeeker/$path")
    }

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(rm)
}

fun Context.getAuthorizeCode() {
    startActivity(
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://github.com/login/oauth/authorize?client_id=${RetrofitInterface.clientId}&scope=repo")
        )
    )
}

fun String.endsWith(prefix: String, comma: Int): Boolean{
    for (value in prefix.split(","))
        if (this.endsWith(value))
            return true
    return false
}