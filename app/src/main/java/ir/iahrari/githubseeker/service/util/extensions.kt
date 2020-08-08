package ir.iahrari.githubseeker.service.util

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import ir.iahrari.githubseeker.R
import ir.iahrari.githubseeker.service.RetrofitInterface

fun Context.getToken(): String {
    return "token " + getSharedPreferences("token", Context.MODE_PRIVATE)
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

fun ImageView.setLanguageLogo(language: String?){
    if (language != null)
        this.setImageResource(
            when (language) {
                "Kotlin" -> R.drawable.ic_kotlin
                "Java" -> R.drawable.ic_java
                "Go" -> R.drawable.ic_go
                else -> R.drawable.ic_github
            }
        )
    else
        this.setImageResource(android.R.drawable.stat_notify_error)
}

fun Uri.downloadFromUri(context: Context, name: String, path: String) {
    //TODO: add Runtime Permission
    val rm = DownloadManager.Request(this)
    rm.apply {
        addRequestHeader("Authorization", context.getToken())
        setTitle("Downloading $name")
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
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

@Suppress("UNUSED_PARAMETER")
fun String.endsWith(prefix: String, comma: Int): Boolean {
    for (value in prefix.split(","))
        if (this.endsWith(value))
            return true
    return false
}