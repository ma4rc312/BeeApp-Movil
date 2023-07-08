package com.marce.beeapp_pro


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isEmpty
import com.google.android.material.snackbar.Snackbar
import com.marce.Interfases.ApiService
import com.marce.beeapp_pro.Models.Login.RequestLogin
import com.marce.beeapp_pro.Models.Login.ResponseLogin
import com.marce.beeapp_pro.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.supportActionBar?.hide()

        clicks()
    }

    private fun clicks() {
        binding.btninicioSesion.setOnClickListener {
            validateData() }
    }

    private fun validateData() {
        val results = arrayOf(validateEmail(), validatePassword())
        if (false in results) {
            return
        }
        sendUser()
    }

    private fun validateEmail(): Boolean {
        return if (binding.edtCorreo.text.toString().isEmpty()) {
            binding.edtCorreo.error = "Este campo es obligatorio"
            false
        } else {
            binding.edtCorreo.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        return if (binding.edtpass.text.toString().isEmpty()) {
            binding.edtpass.error = "Este campo es obligatorio"
            false
        } else {
            binding.edtpass.error = null
            true
        }
    }

    private fun sendUser() {
        val retro = Retrofit.Builder()
            .baseUrl("https://bee.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApiService = retro.create(ApiService::class.java)
        service.postLogin(
            RequestLogin(
                binding.edtCorreo.text.toString(),
                binding.edtpass.text.toString()
            )
        ).enqueue(object :
            Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                Log.e("onResponse: ", response.body().toString())
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("onFailure: ", t.toString())
                Snackbar.make(binding.root,"Error de servidor", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

}