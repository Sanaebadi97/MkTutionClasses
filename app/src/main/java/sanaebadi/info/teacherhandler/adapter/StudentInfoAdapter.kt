package sanaebadi.info.teacherhandler.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.stu_info_add_row.view.*
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.activity.StudentInfoDetails
import sanaebadi.info.teacherhandler.database.student.Student

class StudentInfoAdapter(private val context: Context, private var mStudentList: MutableList<Student>?) :
    RecyclerView.Adapter<StudentInfoAdapter.StudentInfoViewHolder>() {

    /*inflate layout*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentInfoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stu_info_add_row, parent, false)
        return StudentInfoViewHolder(view)
    }

    /*get list size*/
    override fun getItemCount(): Int {
        var listSize = mStudentList!!.size
        Log.i("ListSizeAdapter", "ListSizeAdapter" + listSize)
        return listSize

    }

    /*bind views*/
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StudentInfoViewHolder, position: Int) {
        val student: Student = mStudentList!![position]
        holder.txtName.text = student.stu_name

        holder.imgMore.setOnClickListener {
            val intent = Intent(context, StudentInfoDetails::class.java)
            intent.putExtra("STU_NAME", student.stu_name)
            intent.putExtra("STU_START_DATE", student.stu_starting_date)
            intent.putExtra("STU_FEE_DUE", student.stu_fee_due)
            intent.putExtra("STU_FEE_DEPOSIT", student.stu_fee_deposit)
            intent.putExtra("STU_TEST_REPORT", student.stu_test_report)
            intent.putExtra("STU_MOBILE_NOM", student.stu_mobil_nom)
            context.startActivity(intent)
        }
    }


    /*view holder class*/
    class StudentInfoViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {


        val txtName = view.txt_stu_name!!
        var cardList = view.card_list!!
        var imgMore = view.img_more!!

        init {
            cardList.setOnCreateContextMenuListener(this)
        }


        /*Long Press On RecyclerView Items*/
        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            if (menu != null) {
                menu.setHeaderTitle(R.string.header)
                menu.add(this.adapterPosition, 121, 0, R.string.delete)
                menu.add(this.adapterPosition, 122, 1, R.string.share)
            }


        }
    }

    /*Remove Item from List*/
    public fun removeItem(position: Int) {
        mStudentList!!.removeAt(position)
        notifyDataSetChanged()
    }

}