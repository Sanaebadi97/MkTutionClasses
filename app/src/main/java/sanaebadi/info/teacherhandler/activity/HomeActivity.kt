package sanaebadi.info.teacherhandler.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.adapter.HomeScreenAdapter
import sanaebadi.info.teacherhandler.databinding.ActivityHomeBinding
import sanaebadi.info.teacherhandler.model.HomeItem
import java.util.*

class HomeActivity : BaseActivity(), HomeScreenAdapter.OnItemClickListener {


    private lateinit var binding: ActivityHomeBinding

    private val homeItemList = ArrayList<HomeItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)


        //RecyclerView config
        binding.rvHomeScreen.setHasFixedSize(true)
        binding.rvHomeScreen.setItemViewCacheSize(20)
        binding.rvHomeScreen.clearOnScrollListeners()
        binding.rvHomeScreen.layoutManager = GridLayoutManager(applicationContext, 2) as RecyclerView.LayoutManager?

        putData()

        val adapter = HomeScreenAdapter(applicationContext, this.homeItemList, this)
        binding.rvHomeScreen.adapter = adapter
    }


    /*Data For Home RecyclerView*/
    private fun putData() {
        homeItemList.add(
            HomeItem(
                getString(R.string.class_room),
                R.mipmap.class_room
            )
        )

        homeItemList.add(
            HomeItem(
                getString(R.string.teacher_staff),
                R.mipmap.teacher
            )
        )
        homeItemList.add(
            HomeItem(
                getString(R.string.students),
                R.mipmap.student
            )
        )


        homeItemList.add(
            HomeItem(
                getString(R.string.report),
                R.mipmap.report
            )
        )


    }

    /*Click On Recycler Items*/
    override fun onItemClick(position: Int, cardView: CardView) {
        when (position) {
            0 -> {
                Toast.makeText(applicationContext, "Students and Classes", Toast.LENGTH_SHORT).show()

            }
            1 -> {
                intent = Intent(applicationContext, TeacherLoginActivity::class.java)
                startActivity(this.intent)

            }
            2 -> {
                intent = Intent(applicationContext, StudentLoginActivity::class.java)
                startActivity(this.intent)
            }

            3 -> {
                Toast.makeText(applicationContext, "Report and Upgrade", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

