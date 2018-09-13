package com.ricardonuma.desafioandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ricardonuma.desafioandroid.repository.RepositoryActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, RepositoryActivity::class.java)
        this.startActivity(intent)
        finish()
    }
}
