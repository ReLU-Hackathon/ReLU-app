package com.lapperapp.laper.User

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.laperapp.laper.ResponseBodyApi
import com.lapperapp.laper.Data.FilterModel
import com.lapperapp.laper.R
import com.lapperapp.laper.TimeAgo
import com.lapperapp.laper.User.Personal.PersonalFragment
import com.lapperapp.laper.ui.chats.Chat.ChatActivity
import de.hdodenhof.circleimageview.CircleImageView
import nl.joery.animatedbottombar.AnimatedBottomBar
import java.util.*

class ProfileActivity : AppCompatActivity() {
    lateinit var userImage: CircleImageView
    lateinit var userName: TextView
    lateinit var chatSection: ImageView
    lateinit var frameLayout: FrameLayout
    lateinit var bottomBar: AnimatedBottomBar
    lateinit var userId: String
    lateinit var verified:ImageView
    lateinit var title: TextView
    lateinit var lastActive: TextView
    var fragment: Fragment? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        frameLayout = findViewById(R.id.profile_frame_container)
        bottomBar = findViewById(R.id.profile_bottom_bar)
        userName = findViewById(R.id.profile_dev_name)
        userImage = findViewById(R.id.profile_dev_image_view)
        chatSection = findViewById(R.id.profile_chat_section)
        verified = findViewById(R.id.user_profile_verified)
        title = findViewById(R.id.user_profile_title)
        lastActive = findViewById(R.id.user_profile_last_active)

        userId = intent.getStringExtra("userId").toString()
        fragment = ExpertInFragment(userId)
        setFrameLayout(fragment)

        chatSection.setOnClickListener {
            val intent = Intent(baseContext, ChatActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }


        bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when (newTab.id) {
                    R.id.navigation_personal -> {
                        fragment = PersonalFragment(userId)
                    }
                    R.id.navigation_expert_in -> {
                        fragment = ExpertInFragment(userId)
                    }
                }
                if (fragment != null) {
                    setFrameLayout(fragment)
                }
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                Log.d("bottom_bar", "Reselected index: $index, title: ${tab.title}")
            }
        })

        getExpertData()

    }


    private fun getExpertData() {

        val model = FilterModel("email", userId, 1, 1)
        ResponseBodyApi.getExpertResponseBody(baseContext,model, onResponse = { json ->
            val expert = json?.expert
            if (expert != null) {
                userName.text = expert[0].name
                Glide.with(baseContext).load(expert[0].userImageUrl).into(userImage)
                title.text = expert[0].desc
                val timeAgo = TimeAgo()
                val currentDate = timeAgo.getTimeAgo(Date(expert[0].lastActive.toLong()), baseContext)
                lastActive.text = currentDate
            }
        }
        ) { t ->
            Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
        }

    }


    fun setFrameLayout(fragment: Fragment?) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        if (fragment != null) {
            fragmentTransaction.replace(frameLayout.id, fragment)
        }
        fragmentTransaction.commit()
    }


}