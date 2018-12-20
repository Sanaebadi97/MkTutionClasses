package sanaebadi.info.teacherhandler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_screen_row.view.*
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.model.HomeItem

class HomeScreenAdapter(
    private val context: Context,
    private val homeItemList: ArrayList<HomeItem>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<HomeScreenAdapter.HomeScreenViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(position: Int, cardView: CardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_screen_row, parent, false)
        return HomeScreenViewHolder(view)

    }

    override fun getItemCount() = homeItemList.size


    override fun onBindViewHolder(holder: HomeScreenViewHolder, position: Int) {
        val homeItem: HomeItem = homeItemList[position]
        holder.txtItem.text = homeItem.txtItem
        val cardItem: CardView = holder.cardItem

        Glide.with(context).load(homeItem.imageView).into(holder.imgItem)

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position, cardItem)
        }
    }

    class HomeScreenViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgItem = view.img_home_item!!
        val txtItem = view.txt_home_item!!
        val cardItem = view.card_home!!
    }
}
