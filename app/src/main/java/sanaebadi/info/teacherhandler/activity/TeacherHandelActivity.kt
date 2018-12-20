package sanaebadi.info.teacherhandler.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivityTeacherHandelBinding

class TeacherHandelActivity : BaseActivity() {
    private lateinit var binding: ActivityTeacherHandelBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_handel)


        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers

    }

    //Handel  View Events Launch Activity
    inner class MyHandlers {
        fun onAddDetailsClick(view: View) {
            val intent = Intent(applicationContext, StudentInfoActivity::class.java)
            startActivity(intent)
        }

        fun onUploadPdfFile(view: View) {
            val intent = Intent(applicationContext, UploadPdfActivity::class.java)
            startActivity(intent)
        }
        fun onBatchTimeClick(view: View) {
            val intent = Intent(applicationContext, TeacherBatchTimeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }
}
