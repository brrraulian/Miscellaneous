package ricardonuma.com.desafio_android

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ricardonuma.com.desafio_android.repository.RepositoryActivity

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, RepositoryActivity::class.java)
        this.startActivity(intent)
        finish()
    }

}
