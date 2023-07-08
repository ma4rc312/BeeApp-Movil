package com.marce.Interfases
import com.marce.beeapp_pro.Models.Login.RequestLogin
import com.marce.beeapp_pro.Models.Login.ResponseLogin
import com.marce.beeapp_pro.Models.Register.ResponseRegister
import com.marce.beeapp_pro.Models.Register.ResquestRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
interface ApiService {
    @POST("login")
    fun postLogin(@Body requestLogin: RequestLogin): Call<ResponseLogin>

    @POST("register")
    fun postRegister(@Body resquestRegister: ResquestRegister): Call<ResponseRegister>


}