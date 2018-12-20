package sanaebadi.info.teacherhandler.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivityShowStudentQueryBinding

class ShowStudentQueryActivity : BaseActivity() {
    private lateinit var binding: ActivityShowStudentQueryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_show_student_query)


        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers
    }

    /*Click Listener On Views*/
    inner class MyHandlers {
        fun onBackClick(view: View) {
            finish()
        }


    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }
}
