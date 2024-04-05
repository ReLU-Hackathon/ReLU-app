package com.lapperapp.laper.ui.NewHome

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.laperapp.laper.ResponseBodyApi
import com.lapperapp.laper.BuildConfig
import com.lapperapp.laper.Categories.*
import com.lapperapp.laper.Data.UserUpdateModel
import com.lapperapp.laper.R
import java.util.*

class NewHomeFragment : Fragment() {
    var data = ArrayList<CategoryModel>()
    val database = Firebase.database
    val tokenRef = database.getReference("token")
    private lateinit var developersCard: CardView
    private lateinit var cameraCard: CardView
    private lateinit var typeCard: CardView
    private lateinit var exploreCard: CardView
    private lateinit var title: TextView

    var imagePicker: ImageView? = null

    @SuppressLint("NotifyDataSetChanged", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_home_fragment, container, false)

        developersCard = view.findViewById(R.id.home_developers)
        cameraCard = view.findViewById(R.id.home_take_photo)
        typeCard = view.findViewById(R.id.home_type_question)
        exploreCard = view.findViewById(R.id.home_explore)
        title = view.findViewById(R.id.home_title)

        imagePicker = view.findViewById(R.id.image_picker)

        val bottomSheet = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val cameraBtn = bottomSheetView.findViewById<CardView>(R.id.camera_btn)
        val galleryBtn = bottomSheetView.findViewById<CardView>(R.id.gallery_btn)

        // set the view for the dialog
        bottomSheet.setContentView(bottomSheetView)

        exploreCard.setOnClickListener { v ->
            val explore = Intent(context, AllCategoryActivity::class.java)
            startActivity(explore)
        }

        cameraCard.setOnClickListener {
            bottomSheet.show()
        }

        cameraBtn.setOnClickListener {
            bottomSheet.cancel()
            ImagePicker.with(this)
                .cameraOnly().crop().maxResultSize(400, 400).start()
        }
        galleryBtn.setOnClickListener {
            bottomSheet.cancel()
            ImagePicker.with(this)
                .galleryOnly()    //User can only select image from Gallery
                .start()
        }

        typeCard.setOnClickListener { v ->
            val explore = Intent(context, TypeQuestActivity::class.java)
            explore.putExtra("image_uri", "");
            startActivity(explore)
        }

        developersCard.setOnClickListener { v ->
            val explore = Intent(context, ViewAllExpertsActivity::class.java)
            startActivity(explore)
        }

        setToken()
        getUserData()

        return view

    }


    private fun getUserData() {
        ResponseBodyApi.getUserResponseBody(requireContext(),
            onResponse = { json ->
                val user = json?.user
                if (user != null) {
                    title.text = "Hello "+user.name
                }
            },
            onFailure = { t ->
//                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            val typeIntent = Intent(context, TypeQuestActivity::class.java)
            typeIntent.putExtra("image_uri", data?.data.toString())
            startActivity(typeIntent)
        }
    }


    fun setToken() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { s ->
            val hashMap = hashMapOf(
                "lastActive" to System.currentTimeMillis(),
                "token" to s,
                "versionName" to BuildConfig.VERSION_NAME,
                "versionCode" to BuildConfig.VERSION_CODE
            )
            for ((key, value) in hashMap) {
                val userUpdateModel = UserUpdateModel(key,value.toString())
                ResponseBodyApi.updateUser(requireContext(),userUpdateModel, onResponse = { res->
                    val user = res?.user
                    if (user!=null){
                        Log.d("res",res.user.toString())
                    }
                }, onFailure = {t->
                    t.printStackTrace()
                })
            }
        }

    }



}