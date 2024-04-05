package com.lapperapp.laper.ui.NewDashboard.NewRequest

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lapperapp.laper.R
import com.lapperapp.laper.utils.TimeAgo
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class NewRequestAdapter(private val mList: List<NewRequestSentModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val db = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.new_request_sent_expert_item, parent, false)
                ExpertViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.new_request_sent_all_item, parent, false)
                AllViewHolder(view)
            }
        }
    }

    fun str(ps: String): String {
        return if (ps.length > 80) {
            ps.substring(0, 80) + "..."
        } else {
            ps
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mList[position].expertId.trim() == "all") {
            1
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = mList[position]

        when (holder) {
            is ExpertViewHolder -> holder.setValues(model)
            is AllViewHolder -> {
                holder.setRequestAllValues(model)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ExpertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val reqImage: CircleImageView =
            itemView.findViewById(R.id.new_request_expert_sent_image)
        private val reqName: TextView = itemView.findViewById(R.id.new_request_expert_sent_name)
        private val reqPs: TextView = itemView.findViewById(R.id.new_request_expert_sent_ps)
        private val reqDate: TextView = itemView.findViewById(R.id.new_request_expert_date)

        fun setValues(model: NewRequestSentModel) {
            val context = itemView.context
            val timeAgo = TimeAgo()
            val currentDate = timeAgo.getTimeAgo(Date(model.reqSentDate), context)

            reqDate.text = currentDate
            reqName.text = model.expName
            reqPs.text = str(model.ps)
            Glide.with(context).load(model.expImage).placeholder(R.drawable.logo).into(reqImage)
            itemView.setOnClickListener {
                val reqIntent = Intent(context, RequestDetailActivity::class.java)
                reqIntent.putExtra("requestId", model.reqId)
                context.startActivity(reqIntent)
            }
        }
    }

    inner class AllViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val reqPs: TextView = itemView.findViewById(R.id.new_request_sent_all_problem_statement)
        private val reqDate: TextView = itemView.findViewById(R.id.new_request_sent_all_post_time)

        fun setRequestAllValues(model: NewRequestSentModel) {
            val context = itemView.context
            val timeAgo = TimeAgo()
            val currentDate = timeAgo.getTimeAgo(Date(model.reqSentDate), context)

            reqDate.text = currentDate
            reqPs.text = model.ps

        }
    }
}
