package org.lenchan139.ddgsearcher

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class OpenFromAssistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_from_assist)
        //startActivity(Intent(this, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }
}
