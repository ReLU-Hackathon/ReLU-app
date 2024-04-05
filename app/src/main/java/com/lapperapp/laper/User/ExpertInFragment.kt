package com.lapperapp.laper.User

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lapperapp.laper.Adapter.DevSkillAdapter
import com.lapperapp.laper.Model.DevSkill
import com.lapperapp.laper.R

class ExpertInFragment(private var userId: String) : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var desc: TextView
    private lateinit var devSkillAdapter: DevSkillAdapter
    private lateinit var devSkill: ArrayList<DevSkill>
    val db = Firebase.firestore
    var adminRef = db.collection("admin")
    var userRef = db.collection("experts")
    var techRef = db.collection("tech")
    val auth = FirebaseAuth.getInstance()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expert_in, container, false)


        recyclerView = view.findViewById(R.id.profile_expert_in_recycler_view)
        desc = view.findViewById(R.id.expert_in_desc)
        recyclerView.layoutManager = LinearLayoutManager(context)

        devSkill = ArrayList()
        devSkillAdapter = DevSkillAdapter(devSkill, userId)

        recyclerView.adapter = devSkillAdapter
        devSkillAdapter.notifyDataSetChanged()

        getUserData()

        return view

    }

    fun getUserData(){

    }



}