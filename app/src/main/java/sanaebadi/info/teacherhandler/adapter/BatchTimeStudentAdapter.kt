package sanaebadi.info.teacherhandler.adapter

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.batch_time_row.view.*
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.database.BatchTime

class BatchTimeStudentAdapter(
    private val context: Context,
    private val batchTimeList: MutableList<BatchTime>
) :
//private val onItemClickListener: BatchTimeStudentAdapter.OnItemClickListener :
    RecyclerView.Adapter<BatchTimeStudentAdapter.BatchTimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatchTimeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.batch_time_row, parent, false)
        return BatchTimeViewHolder(view)
    }

    override fun getItemCount() = batchTimeList.size

    override fun onBindViewHolder(holder: BatchTimeViewHolder, position: Int) {
        val batchTime: BatchTime = batchTimeList[position]
        holder.txtTimeBatchTitle.text = batchTime.title_time
        holder.txtTimeBatchOne.text = batchTime.first_time
        holder.txtTimeBatchTwo.text = batchTime.second_time


    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class BatchTimeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {


        val txtTimeBatchTitle = view.txt_time_batch_title!!
        val txtTimeBatchOne = view.txt_time_batch_one!!
        val txtTimeBatchTwo = view.txt_time_batch_two!!
        val batchTimeCard = view.batch_time_card!!

        init {
            batchTimeCard.setOnCreateContextMenuListener(this)
        }

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
        batchTimeList.removeAt(position)
        notifyDataSetChanged()
    }
}