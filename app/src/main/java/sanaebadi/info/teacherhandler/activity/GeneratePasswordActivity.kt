package sanaebadi.info.teacherhandler.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivityGeneratePasswordBinding

class GeneratePasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityGeneratePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_generate_password)
    }
}
