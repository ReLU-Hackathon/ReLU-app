package com.lapperapp.laper

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lapperapp.laper.ui.NewDashboard.NewRequest.NewRequestAdapter
import com.lapperapp.laper.ui.NewDashboard.NewRequest.NewRequestSentModel
import com.lapperapp.laper.ui.NewHome.SelectCategorymodel

class AllRequestsActivity : AppCompatActivity() {
    private lateinit var reqRecyclerView: RecyclerView
    var db = Firebase.firestore
    var userRef = db.collection("users")
    var expertsRef = db.collection("experts")
    var reqRef = db.collection("requests")
    var auth = FirebaseAuth.getInstance()
    private lateinit var reqSentModelModel: ArrayList<NewRequestSentModel>
    private lateinit var reqSentAdapter: NewRequestAdapter
    private lateinit var toolbar:Toolbar
    private lateinit var lotti:LottieAnimationView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_requests)

        toolbar = findViewById(R.id.request_toolbar)
        lotti = findViewById(R.id.all_requests_empty_lotti)
        reqRecyclerView = findViewById(R.id.all_requests_recycler_view)
        reqRecyclerView.layoutManager = LinearLayoutManager(baseContext)
        reqSentModelModel = ArrayList()
        reqSentAdapter = NewRequestAdapter(reqSentModelModel)
        reqRecyclerView.adapter = reqSentAdapter

        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }




    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }


}