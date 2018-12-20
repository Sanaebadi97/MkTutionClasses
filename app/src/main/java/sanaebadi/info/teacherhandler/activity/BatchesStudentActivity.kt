package sanaebadi.info.teacherhandler.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.content_main.*

import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.adapter.ExpandListViewAdapter
import sanaebadi.info.teacherhandler.databinding.ActivityBatchesStudentsBinding

class BatchesStudentActivity : BaseActivity() {

    private lateinit var binding: ActivityBatchesStudentsBinding
    val header: MutableList<String> = ArrayList()
    private val body: MutableList<MutableList<String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_batches_students)


        /*Define Some Data For Child In Expand List View*/
        val season1: MutableList<String> = ArrayList()
        season1.add(getString(R.string.season1_child_1))
        season1.add(getString(R.string.season1_child_2))
        season1.add(getString(R.string.season1_child_3))
        season1.add(getString(R.string.season1_child_4))


        val season2: MutableList<String> = ArrayList()
        season2.add(getString(R.string.season2_child_1))
        season2.add(getString(R.string.season2_child_2))
        season2.add(getString(R.string.season2_child_3))
        season2.add(getString(R.string.season2_child_4))


        val season3: MutableList<String> = ArrayList()
        season3.add(getString(R.string.season3_child_1))
        season3.add(getString(R.string.season3_child_2))
        season3.add(getString(R.string.season3_child_3))
        season3.add(getString(R.string.season3_child_4))


        val season4: MutableList<String> = ArrayList()
        season4.add(getString(R.string.season4_child_1))
        season4.add(getString(R.string.season4_child_2))
        season4.add(getString(R.string.season4_child_3))
        season4.add(getString(R.string.season4_child_4))


        val season5: MutableList<String> = ArrayList()
        season5.add(getString(R.string.season5_child_1))
        season5.add(getString(R.string.season5_child_2))
        season5.add(getString(R.string.season5_child_3))
        season5.add(getString(R.string.season5_child_4))


        /*Define Some Data For Header In Expand List*/
        header.add(getString(R.string.season1))
        header.add(getString(R.string.season2))
        header.add(getString(R.string.season3))
        header.add(getString(R.string.season4))
        header.add(getString(R.string.season5))

        body.add(season1)
        body.add(season2)
        body.add(season3)
        body.add(season4)
        body.add(season5)


        expanded_list_view.setAdapter(ExpandListViewAdapter(this, expanded_list_view, header, body))


        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers

    }


    //Handel  View Events Launch Activity
    inner class MyHandlers {
        fun onQueryClick(view: View) {
            val intent = Intent(applicationContext, QueryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }
}
