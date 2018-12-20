package sanaebadi.info.teacherhandler.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivityQueryBinding

class QueryActivity : BaseActivity() {
    private lateinit var binding: ActivityQueryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_query)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }
}
