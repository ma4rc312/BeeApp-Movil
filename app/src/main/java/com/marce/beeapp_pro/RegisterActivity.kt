package com.marce.beeapp_pro


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.marce.Interfases.ApiService
import com.marce.beeapp_pro.Models.Register.ResponseRegister
import com.marce.beeapp_pro.Models.Register.ResquestRegister
import com.marce.beeapp_pro.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.hide()


        cliks()
    }

    private fun cliks() {
        binding.btnRegister.setOnClickListener {
            validateData()
            val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
              startActivity(intent)
              finish()
        }

    }

    private fun validateData() {
        val results = arrayOf(
            validateName(),
            validatePassword(),
            validateEmail(),
            validateConfPasswo(),
            validateCel()
        )
        if (false in results) {
            return
        }
        sendUser()
    }

    private fun validateName(): Boolean {
        return if (binding.edtNombre.text.toString().isEmpty()) {
            binding.edtNombre.error = "Este campo es obligatorio"
            false
        } else {
            binding.edtNombre.error = null
            true
        }
    }

    private fun validateEmail(): Boolean {
        return if (binding.edtEmail.text.toString().isEmpty()) {
            binding.edtEmail.error = "Este campo es obligatorio"
            false
        } else {
            binding.edtEmail.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        return if (binding.edtPassword.text.toString().isEmpty()) {
            binding.edtPassword.error = "Este campo es obligatorio"
            false
        } else {
            binding.edtPassword.error = null
            true
        }
    }

    private fun validateConfPasswo(): Boolean {
        return if (binding.edtConPassword.text.toString().isEmpty()) {
            binding.edtConPassword.error = "Este campo es obligatorio"
            false
        } else {
            binding.edtConPassword.error = null
            true
        }
    }

    private fun validateCel(): Boolean {
        return if (binding.edtCel.text.toString().isEmpty()) {
            binding.edtCel.error = "Este campo es obligatorio"
            false
        } else {
            binding.edtCel.error = null
            true
        }
    }

    private fun sendUser() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://bee.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
//        val retro = Retrofit.Builder()
//            .baseUrl("https://bee.up.railway.app/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)
        service.postRegister(
            ResquestRegister(
                binding.edtNombre.text.toString(),
                binding.edtEmail.text.toString(),
                binding.edtCel.text.toString(),
                binding.edtPassword.text.toString(),
                binding.edtConPassword.text.toString(),
            )
        ).enqueue(object  : Callback<ResponseRegister>{
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                Toast.makeText(applicationContext, "Registro exitoso",Toast.LENGTH_LONG).show()
                Log.e("onResponse: ",response.body().toString() )

            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                Log.e("onFailure: ",t.toString() )
            }

        })
    }


}