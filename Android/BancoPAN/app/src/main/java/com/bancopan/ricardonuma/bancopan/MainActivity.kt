package com.bancopan.ricardonuma.bancopan

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bancopan.ricardonuma.bancopan.game.GameListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, GameListActivity::class.java)
        this.startActivity(intent)
        finish()
    }
}
