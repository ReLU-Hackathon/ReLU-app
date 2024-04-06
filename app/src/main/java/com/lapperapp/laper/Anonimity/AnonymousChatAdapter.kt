package com.lapperapp.laper.Anonimity

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.lapperapp.laper.R
import com.lapperapp.laper.ui.chats.Chat.ChatModel
import com.lapperapp.laper.utils.TimeAgo
import java.util.Date

class AnonymousChatAdapter(private val mList: List<ChatModel>, private val receiverUserId: String) :
    RecyclerView.Adapter<AnonymousChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.anonymous_chat, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val model = mList[position]
        val timeAgo = TimeAgo()
        val currentDate = timeAgo.getTimeAgo(Date(model.date), context)
        val sharedPreferences = context.getSharedPreferences("credential", Context.MODE_PRIVATE)
        val senderId = sharedPreferences.getString("email", null)

//        holder.anonId.text = model.reciverId

        if (model.reciverId.trim().equals(senderId)) {
            // SENDER USER
            holder.sendLinear.visibility = View.VISIBLE
            holder.sendText.text = model.text
            holder.sendDate.text = currentDate

            holder.recLinear.visibility = View.GONE
        } else {
            // RECEIVER USER
            holder.recLinear.visibility = View.VISIBLE
            holder.recText.text = model.text
            holder.recDate.text = currentDate

            holder.sendLinear.visibility = View.GONE
        }
        holder.itemView.setOnLongClickListener{
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", model.text)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context,"copied!", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        // RECEIVER ItemView
        val anonId: TextView = itemView.findViewById(R.id.anon_id)
        val recText: TextView = itemView.findViewById(R.id.anon_receiver_text_message_item)
        val recDate: TextView = itemView.findViewById(R.id.anon_receiver_date_message_item)
        val recLinear: LinearLayout = itemView.findViewById(R.id.anon_receiver_linear_message_item)

        // SENDER ItemView
        val sendText: TextView = itemView.findViewById(R.id.anon_sender_text_message_item)
        val sendDate: TextView = itemView.findViewById(R.id.anon_sender_date_message_item)
        val sendLinear: LinearLayout = itemView.findViewById(R.id.anon_sender_linear_message_item)
    }


}