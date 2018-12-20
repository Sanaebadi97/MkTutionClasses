package sanaebadi.info.teacherhandler.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.query_row.view.*
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.database.query.Querys

class QueryAdapter(private val context: Context, private var queryList: MutableList<Querys>?) :
    RecyclerView.Adapter<QueryAdapter.QueryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.query_row, parent, false)
        return QueryViewHolder(view)
    }

    override fun getItemCount() = queryList!!.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QueryViewHolder, position: Int) {
        val querys: Querys = queryList!![position]
        holder.txtMessage.text = querys.query_message
        holder.txtStudentName.text = "Student Name : " + querys.query_stu_name
    }

    inner class QueryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtMessage = view.txt_query_message!!
        val txtStudentName = view.txt_student_name!!
    }
}