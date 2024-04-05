package com.lapperapp.laper.ui.NewDashboard.NewRequest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.laperapp.laper.ResponseBodyApi
import com.lapperapp.laper.Data.FilterModel
import com.lapperapp.laper.R
import com.lapperapp.laper.ui.NewDashboard.TagAdapter
import com.lapperapp.laper.ui.NewHome.SelectCategorymodel
import com.lapperapp.laper.utils.TimeAgo
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class RequestDetailActivity : AppCompatActivity() {
    val db = Firebase.firestore
    var auth = FirebaseAuth.getInstance()
    var expertRef = db.collection("experts")
    var reqRef = db.collection("requests")
    var techRef = db.collection("tech")

    lateinit var userName: String
    lateinit var problemStatement: String
    lateinit var imgUrl: String
    lateinit var expertId: String
    lateinit var reqId: String
    var requestTime by Delegates.notNull<Long>()
    lateinit var tags: ArrayList<String>

    lateinit var nameView: TextView
    lateinit var descView: TextView
    lateinit var dateView: TextView
    lateinit var userImgView: CircleImageView

    lateinit var tagRecyclerview: RecyclerView
    lateinit var tagAdapter: TagAdapter
    lateinit var tagList: ArrayList<SelectCategorymodel>
    private lateinit var cancelBtn: TextView
    private lateinit var psImage:ImageView

    private lateinit var toolbar: Toolbar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_detail)

        nameView = findViewById(R.id.request_detail_user_name)
        descView = findViewById(R.id.request_detail_desc)
        userImgView = findViewById(R.id.request_detail_user_image)
        dateView = findViewById(R.id.request_detail_user_date)
        tagRecyclerview = findViewById(R.id.request_detail_tech_recyclerview)
        cancelBtn = findViewById(R.id.request_detail_cancel_btn)
        psImage = findViewById(R.id.request_detail_image)

        toolbar = findViewById<Toolbar>(R.id.request_detail_toolbar)
        setSupportActionBar(toolbar)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }

        tagList = ArrayList<SelectCategorymodel>()
        tagAdapter = TagAdapter(tagList)
        val llm = LinearLayoutManager(baseContext)
        llm.orientation = RecyclerView.HORIZONTAL
        tagRecyclerview.layoutManager = llm
        tagRecyclerview.adapter = tagAdapter
        tagAdapter.notifyDataSetChanged()

        cancelBtn.setOnClickListener {
        }


        reqId = intent.getStringExtra("requestId").toString()
        fetchRequestDetail(reqId)


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }



    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    fun fetchRequestDetail(reqId: String) {
        val filter = FilterModel("requestId", reqId)
        ResponseBodyApi.fetchRequest(filter, onResponse = { res->
            val req = res?.request
            if (req != null) {
                if (!req.isEmpty()) {
                        descView.setText(req[0].problemStatement)
                        for(tag in req[0].requiredTech){
                            tagList.add(SelectCategorymodel("","",""))
                        }

                    val requestTime:Long = req[0].requestTime.toLong() // Use safe call operator ?. to handle null
                    val timeAgo = TimeAgo()
                    val currentDate = timeAgo.getTimeAgo(Date(requestTime), baseContext)
                    dateView.text = currentDate
                    tagAdapter.notifyDataSetChanged()
                }
            }
        }, onFailure = {t->
            t.printStackTrace()
        })
    }
}