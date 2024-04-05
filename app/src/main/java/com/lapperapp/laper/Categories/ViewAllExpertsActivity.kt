package com.lapperapp.laper.Categories

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laperapp.laper.ResponseBodyApi
import com.lapperapp.laper.R

class ViewAllExpertsActivity : AppCompatActivity() {
    lateinit var devAdapter: DeveloperAdapter
    private lateinit var recyclerView: RecyclerView
    var devData = ArrayList<DevModel>()
    private lateinit var toolbar: Toolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_experts)
        recyclerView = findViewById(R.id.view_all_expert_recycler_view)
        toolbar = findViewById(R.id.view_all_expert_toolbar)
        devData = ArrayList()
        devAdapter = DeveloperAdapter(devData)
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
        recyclerView.adapter = devAdapter

        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }


        getUsers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }


    @SuppressLint("NotifyDataSetChanged")
    fun getUsers() {
        ResponseBodyApi.getExperts(baseContext,
            onResponse = { json ->
                val expert = json?.expert
                if (expert != null) {
                    for(ex in expert){
                        devData.add(DevModel(ex.name,ex.userImageUrl,ex.email,0,ex.desc))
                        devAdapter.notifyDataSetChanged()
                    }
                }
            },
            onFailure = { t ->
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        )

    }


}