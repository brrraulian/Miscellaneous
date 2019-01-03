package com.example.koki.reactiontimer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.koki.reactiontimer.Util.PrefUtil
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        loadEditText()
        setEditTextTextChanged()
    }

    private fun loadEditText() {
        edit_text_countdown.setText(PrefUtil.getSecondsCountdown(this).toString())
        edit_text_random_min.setText(PrefUtil.getSecondsRandomMin(this).toString())
        edit_text_random_max.setText(PrefUtil.getSecondsRandomMax(this).toString())
    }

    private fun setEditTextTextChanged() {
        setEditTextCountdownTextChanged()
        setEditTextRandomMinTextChanged()
        setEditTextRandomMaxTextChanged()
    }

    private fun setEditTextCountdownTextChanged() {
        edit_text_countdown.afterTextChanged {
            if (!edit_text_countdown.text.isEmpty() && edit_text_countdown.text.toString().toInt() > 0)
                PrefUtil.setSecondsCountdown(it.toInt(), this)
        }
    }

    private fun setEditTextRandomMinTextChanged() {
        edit_text_random_min.afterTextChanged {
            if (!edit_text_random_min.text.isEmpty() && edit_text_random_min.text.toString().toInt() > 0)
                PrefUtil.setSecondsRandomMin(it.toInt(), this)
        }
    }

    private fun setEditTextRandomMaxTextChanged() {
        edit_text_random_max.afterTextChanged {
            if (!edit_text_random_max.text.isEmpty() && edit_text_random_max.text.toString().toInt() > 0)
                PrefUtil.setSecondsRandomMax(it.toInt(), this)
        }
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}