package com.lapperapp.laper.ui.NewDashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.laperapp.laper.ResponseBodyApi
import com.laperapp.laper.api.RetrofitClient
import com.lapperapp.laper.Categories.ViewAllExpertsActivity
import com.lapperapp.laper.Data.FilterModel
import com.lapperapp.laper.PSRequest.FetchRequestModel
import com.lapperapp.laper.R
import com.lapperapp.laper.ui.NewDashboard.NewAvailableExpert.NewAvailableExpertAdapter
import com.lapperapp.laper.ui.NewDashboard.NewAvailableExpert.NewAvailableExpertModel
import com.lapperapp.laper.ui.NewDashboard.NewRequest.NewRequestAdapter
import com.lapperapp.laper.ui.NewDashboard.NewRequest.NewRequestSentModel
import com.lapperapp.laper.ui.NewHome.SelectCategorymodel
import com.lapperapp.laper.ui.chats.AllChatsActivity
import nl.joery.animatedbottombar.AnimatedBottomBar
import javax.security.auth.callback.Callback


class NewDashboardFragment(
    private val bottomBar: AnimatedBottomBar, private val tabToAddBadgeAt: AnimatedBottomBar.Tab
) : Fragment() {
    private lateinit var reqRecyclerView: RecyclerView
    private lateinit var aeRecyclerView: RecyclerView
    var db = Firebase.firestore
    var userRef = db.collection("users")
    var auth = FirebaseAuth.getInstance()
    private lateinit var allChats: CardView
    private lateinit var reqSentModelModel: ArrayList<NewRequestSentModel>
    private lateinit var reqSentAdapter: NewRequestAdapter

    private lateinit var availableExpertModel: ArrayList<NewAvailableExpertModel>
    private lateinit var availableExpertAdapter: NewAvailableExpertAdapter
    private lateinit var uReqIds: ArrayList<String>
    private lateinit var uAvaExpertIds: ArrayList<String>
    private lateinit var findDeveloper: CardView
    private lateinit var sharedPreferences: SharedPreferences


    @SuppressLint("NotifyDataSetChanged", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_dashboard, container, false)
        uReqIds = ArrayList()
        uAvaExpertIds = ArrayList()

        findDeveloper = view.findViewById(R.id.dash_find_developers)

        reqRecyclerView = view.findViewById(R.id.dashboard_new_request_recycler_view)
        reqRecyclerView.layoutManager = LinearLayoutManager(context)
        reqSentModelModel = ArrayList()
        reqSentAdapter = NewRequestAdapter(reqSentModelModel)
        reqRecyclerView.adapter = reqSentAdapter

        aeRecyclerView = view.findViewById(R.id.dashboard_available_expert_recycler_view)
        aeRecyclerView.layoutManager = LinearLayoutManager(context)
        availableExpertModel = ArrayList()
        availableExpertAdapter = NewAvailableExpertAdapter(availableExpertModel)
        aeRecyclerView.adapter = availableExpertAdapter
        availableExpertAdapter.notifyDataSetChanged()

        allChats = view.findViewById(R.id.dash_all_chats)

        allChats.setOnClickListener {
            val intent = Intent(context, AllChatsActivity::class.java)
            startActivity(intent)
        }

        findDeveloper.setOnClickListener {
            val intent = Intent(context, ViewAllExpertsActivity::class.java)
            startActivity(intent)
        }

        fetchSentRequest()

//        fetchMyRequests()

        return view
    }

    override fun onStart() {
        super.onStart()

        clearNotification()
    }


    fun clearNotification() {
        userRef.document(auth.uid.toString()).update("dashboardNotification", false)
        bottomBar.clearBadgeAtTab(tabToAddBadgeAt)
    }

    fun fetchSentRequest() {
        // Define your FilterModel
        val filter = FilterModel(field = "clientId", value = "user@gmail.com")
        val ret = arrayOf(SelectCategorymodel("Python", "", "123456"))

        // Obtain an instance of ApiInterface
        val apiService = RetrofitClient.getClient()
//        Toast.makeText(context,"fetching",Toast.LENGTH_SHORT).show()

        apiService.fetchRequest(filter).enqueue(object : retrofit2.Callback<FetchRequestModel> {
            override fun onResponse(
                call: retrofit2.Call<FetchRequestModel>,
                response: retrofit2.Response<FetchRequestModel>
            ) {
                if (response.isSuccessful) {
                    val fetchRequestModel: FetchRequestModel? = response.body()
                    val req = fetchRequestModel?.request
                    if (req != null) {
                        for (model in req) {
                            reqSentModelModel.add(
                                NewRequestSentModel(
                                    model.requestTime.toLong(),
                                    "all",
                                    model.requestId,
                                    "",
                                    "",
                                    model.problemStatement,
                                    ret
                                )
                            )
//                                ResponseBodyApi.getExpertResponseBody(
//                                    requireContext(),
//                                    FilterModel("expertId", model.expertId),
//                                    onResponse = { exres ->
//                                        val expertName = exres?.expert?.get(0)?.name.toString()
//                                        val expertImageUrl =
//                                            exres?.expert?.get(0)?.userImageUrl.toString()
//
//                                        reqSentModelModel.add(
//                                            NewRequestSentModel(
//                                                model.requestTime.toLong(),
//                                                model.expertId,
//                                                model.requestId,
//                                                expertName,
//                                                expertImageUrl,
//                                                model.problemStatement,
//                                                ret
//                                            )
//                                        )
//                                    },
//                                    onFailure = { t ->
//                                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT)
//                                            .show()
//                                    })
                            uReqIds.add("")
                            reqSentAdapter.notifyDataSetChanged()
                        }
                    }

                    Toast.makeText(context, "successfull", Toast.LENGTH_SHORT).show()
                } else {
                    val errorMessage = "Response unsuccessful: ${response.code()}"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<FetchRequestModel>, t: Throwable) {
                Toast.makeText(context, "Failed ${t.message.toString()}", Toast.LENGTH_SHORT).show()
            }
        })

    }


    @SuppressLint("NotifyDataSetChanged")
    fun fetchMyRequests() {

        val arr = ArrayList<String>()
        arr.add("python")
        val filter = FilterModel("clientId", "user@gmail.com", lim = 6)
        val ret = arrayOf(SelectCategorymodel("Python", "", "123456"))

        ResponseBodyApi.fetchRequest(filter, onResponse = { res ->
            val req = res?.request
            if (req != null) {
                for (model in req) {
                    if (model.expertId == "all") {
                        reqSentModelModel.add(
                            NewRequestSentModel(
                                model.requestTime.toLong(),
                                model.expertId,
                                model.requestId,
                                "",
                                "",
                                model.problemStatement,
                                ret
                            )
                        )
                    } else {
                        ResponseBodyApi.getExpertResponseBody(
                            requireContext(),
                            FilterModel("expertId", model.expertId),
                            onResponse = { exres ->
                                val expertName = exres?.expert?.get(0)?.name.toString()
                                val expertImageUrl = exres?.expert?.get(0)?.userImageUrl.toString()

                                reqSentModelModel.add(
                                    NewRequestSentModel(
                                        model.requestTime.toLong(),
                                        model.expertId,
                                        model.requestId,
                                        expertName,
                                        expertImageUrl,
                                        model.problemStatement,
                                        ret
                                    )
                                )
                            },
                            onFailure = { t ->
                                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                            })
                    }
                    uReqIds.add("")
                    reqSentAdapter.notifyDataSetChanged()

//                    ResponseBodyApi.getExpertResponseBody(
//                        requireContext(),
//                        FilterModel("expertId", model.expertId),
//                        onResponse = { exres ->
//                            val expertName = exres?.expert?.get(0)?.name.toString()
//                            val expertImageUrl = exres?.expert?.get(0)?.userImageUrl.toString()
//
//                            reqSentModelModel.add(
//                                NewRequestSentModel(
//                                    model.requestTime.toLong(),
//                                    model.expertId,
//                                    model.requestId,
//                                    expertName,
//                                    expertImageUrl,
//                                    model.problemStatement,
//                                    ret
//                                )
//                            )
//                            uReqIds.add("")
//                            reqSentAdapter.notifyDataSetChanged()
//                        },
//                        onFailure = { t ->
//                            Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
//                        })
                }
            }
        }, onFailure = { t ->
            Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
        })


    }

}

