package ricardonuma.com.desafio_android.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import ricardonuma.com.desafio_android.R

/**
 * Created by ricardonuma on 12/18/17.
 */

open class BaseActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    var toolbar: Toolbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate Base")

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        showActionCanceled = true
        if (intent.extras != null) {
            if (intent.extras.get("show_action_canceled") != null) {
                showActionCanceled = intent.extras.getBoolean("show_action_canceled")
            }
        }

    }

    open fun initData() {

    }

    open fun initUI() {
        toolbar = findViewById(R.id.toolbar)

        if (toolbar != null)
            setSupportActionBar(toolbar)
    }

    override fun setSupportActionBar(@Nullable toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun finish() {
        super.finish()
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun onLoadingStart() {

        onLoadingFinish()
        progressDialog = ProgressDialog(this@BaseActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
        progressDialog!!.setMessage(getString(R.string.title_wait))
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    fun onLoadingFinish() {

        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }
    }

    public override fun onStart() {
        super.onStart()

    }

    public override fun onStop() {
        super.onStop()
    }

    fun APIErrorTreatment(msg: String?, defaultMsg: String) {

        if (msg!!.contains(getResources().getString(R.string.error_message_timeout)) ||
                msg!!.contains(getResources().getString(R.string.error_message_timeout2)) ||
                msg!!.contains(getResources().getString(R.string.error_message_timeout3)) ||
                msg!!.contains(getResources().getString(R.string.error_message_timeout4)) ||
                msg!!.contains(getResources().getString(R.string.error_message_timeout5)))
            Toast.makeText(this, getResources().getString(R.string.message_timeout), Toast.LENGTH_LONG).show()
        else if (msg!!.isEmpty())
            Toast.makeText(this, defaultMsg, Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }


    companion object {

        private val TAG = "BaseActivity"

        private var showActionCanceled: Boolean = false

    }

}