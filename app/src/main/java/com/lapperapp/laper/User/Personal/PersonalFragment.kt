package com.lapperapp.laper.User.Personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.laperapp.laper.ResponseBodyApi
import com.lapperapp.laper.Data.FilterModel
import com.lapperapp.laper.R
import com.lapperapp.laper.utils.TimeAgo
import java.util.*

class PersonalFragment(private var userId: String) : Fragment() {
    lateinit var userEmail: TextView
    lateinit var userPhone: TextView
    lateinit var userAbout: TextView
    lateinit var userCountry: TextView
    lateinit var lastActive: TextView
    lateinit var gender:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_personal, container, false)

        userEmail = view.findViewById(R.id.profile_about_email)
        userPhone = view.findViewById(R.id.profile_about_phone)
        userAbout = view.findViewById(R.id.profile_personal_about)
        userCountry = view.findViewById(R.id.profile_about_country)
        lastActive = view.findViewById(R.id.profile_last_active)
        gender = view.findViewById(R.id.profile_about_gender)

        getUserData()

        return view
    }

    private fun getUserData() {
        val model = FilterModel("email", userId, 1, 1)
        ResponseBodyApi.getExpertResponseBody(requireContext(),model, onResponse = { json ->
            val expert = json?.expert
            if (expert != null) {
                userEmail.text = expert[0].email
                userPhone.text = expert[0].phoneNumber
                userAbout.text = expert[0].desc

                val timeAgo = TimeAgo()
                val currentDate = timeAgo.getTimeAgo(Date(expert[0].lastActive.toLong()), requireContext())
                lastActive.text = currentDate
            }

        }, onFailure = { t ->
            Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
        })

    }

}
