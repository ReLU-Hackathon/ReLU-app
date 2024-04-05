package com.laperapp.laper

import android.content.Context
import com.laperapp.laper.Data.LoginModel
import com.laperapp.laper.Data.LoginResponse
import com.laperapp.laper.Data.SignUpModel
import com.laperapp.laper.Data.UserBase
import com.laperapp.laper.api.RetrofitClient
import com.lapperapp.laper.Data.*
import com.lapperapp.laper.PSRequest.FetchRequestModel
import com.lapperapp.laper.PSRequest.RequestModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ResponseBodyApi {

    fun logInResponseBody(model: LoginModel, onResponse: (String?) -> Unit, onFailure: (Throwable) -> Unit) {
        val jsonapi = RetrofitClient.getClient()
        jsonapi.logIn(model).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    onResponse(response.body()?.token)
                } else {
                    onFailure(Throwable("Response unsuccessful"))
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun signUpResponseBody(model: SignUpModel, onResponse: (String?) -> Unit, onFailure: (Throwable) -> Unit) {
        val jsonapi = RetrofitClient.getClient()
        jsonapi.signUp(model).enqueue(object : Callback<SignUpModel> {
            override fun onResponse(call: Call<SignUpModel>, response: Response<SignUpModel>) {
                if (response.isSuccessful) {
                    onResponse("Sign Up Successful")
                } else {
                    onFailure(Throwable("Response unsuccessful "+response.message()))
                }
            }
            override fun onFailure(call: Call<SignUpModel>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun getUserResponseBody(context: Context, onResponse: (UserBase?) -> Unit, onFailure: (Throwable) -> Unit) {
        val token = RetrofitClient.getCredential("token",context)
        val jsonapi = RetrofitClient.getClient()
        jsonapi.getUserData(token).enqueue(object : Callback<UserBase> {
            override fun onResponse(call: Call<UserBase>, response: Response<UserBase>) {
                if (response.isSuccessful) {
                    val userFetch: UserBase? = response.body()
                    onResponse(userFetch)
                } else {
                    onFailure(Throwable("Response unsuccessful"))
                }
            }

            override fun onFailure(call: Call<UserBase>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun getExperts(context: Context, onResponse: (ExpertBase?) -> Unit, onFailure: (Throwable) -> Unit) {
        val token = RetrofitClient.getCredential("token",context)
        val jsonapi = RetrofitClient.getClient()
        jsonapi.getExperts(token).enqueue(object : Callback<ExpertBase> {
            override fun onResponse(call: Call<ExpertBase>, response: Response<ExpertBase>) {
                if (response.isSuccessful) {
                    val userFetch: ExpertBase? = response.body()
                    onResponse(userFetch)
                } else {
                    onFailure(Throwable("Response unsuccessful "+response.message()))
                }
            }

            override fun onFailure(call: Call<ExpertBase>, t: Throwable) {
                onFailure(t)
            }
        })
    }



    fun getExpertResponseBody(context: Context, model:FilterModel, onResponse: (ExpertBase?) -> Unit, onFailure: (Throwable) -> Unit) {
        val token = RetrofitClient.getCredential("token",context)
        val jsonapi = RetrofitClient.getClient()
        jsonapi.getExpertData(token,model).enqueue(object : Callback<ExpertBase> {
            override fun onResponse(call: Call<ExpertBase>, response: Response<ExpertBase>) {
                if (response.isSuccessful) {
                    val expertFetch: ExpertBase? = response.body()
                    onResponse(expertFetch)
                } else {
                    onFailure(Throwable("Response unsuccessful"))
                }
            }

            override fun onFailure(call: Call<ExpertBase>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun postRequest(context: Context,request: RequestModel, onResponse: (String?) -> Unit, onFailure: (Throwable) -> Unit){
        val token = RetrofitClient.getCredential("token", context)
        val jsonapi = RetrofitClient.getClient()
        jsonapi.postRequest(token,request).enqueue(object : Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                if (response.isSuccessful) {
                    val message: String? = response.message()
                    onResponse(message)
                } else {
                    val errorMessage = "Response unsuccessful: ${response.code()}"
                    onFailure(Throwable(errorMessage))                }
            }
            override fun onFailure(call: Call<Message>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun fetchRequest(filterModel: FilterModel,onResponse: (FetchRequestModel?) -> Unit, onFailure: (Throwable) -> Unit){
        val jsonapi = RetrofitClient.getClient()
        jsonapi.fetchRequest(filterModel).enqueue(object : Callback<FetchRequestModel> {
            override fun onResponse(call: Call<FetchRequestModel>, response: Response<FetchRequestModel>) {
                if (response.isSuccessful) {
                    val fetchRequestModel: FetchRequestModel? = response.body()
                    onResponse(fetchRequestModel)
                } else {
                    val errorMessage = "Response unsuccessful: ${response.code()}"
                    onFailure(Throwable(errorMessage))                }
            }
            override fun onFailure(call: Call<FetchRequestModel>, t: Throwable) {
                onFailure(t)
            }
        })
    }


    fun updateUser(context: Context,updateModel: UserUpdateModel, onResponse: (UserBase?) -> Unit, onFailure: (Throwable) -> Unit){
        val token = RetrofitClient.getCredential("token",context)
        val jsonapi = RetrofitClient.getClient()
        jsonapi.updateUser(token,updateModel).enqueue(object : Callback<UserBase> {
            override fun onResponse(call: Call<UserBase>, response: Response<UserBase>) {
                if (response.isSuccessful) {
                    val userFetch: UserBase? = response.body()
                    onResponse(userFetch)
                } else {
                    val errorMessage = "Response unsuccessful: ${response.code()}"
                    onFailure(Throwable(errorMessage))                }
            }
            override fun onFailure(call: Call<UserBase>, t: Throwable) {
                onFailure(t)
            }
        })
    }


}