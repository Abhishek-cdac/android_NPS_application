package com.nectar.nps

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.google.gson.JsonObject
import com.nectar.nps.utils.PrefUtils
import com.nectarinfotel.utils.AppConstants
import com.nectarinfotel.utils.NectarApplication
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.livekpi_layout.*
import kotlinx.android.synthetic.main.login_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class LoginActivity : AppCompatActivity() {
    private val sharedPrefFile = "kotlinsharedpreference"
    companion object {

        public  var access_token = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.login_layout)
        setvalueforrememberme()
        login_button.setOnClickListener { view: View ->

            if(username.text.toString().length==0)
            {
                Toast.makeText(applicationContext, "Please enter username", Toast.LENGTH_SHORT).show()
            }
            else  if(password.text.toString().length==0)
            {
                Toast.makeText(applicationContext, "Please enter password", Toast.LENGTH_SHORT).show()
            }
            else
            {

                loading.visibility=View.VISIBLE
           var hashMap : HashMap<String, String>
                    = HashMap<String, String> ()
            hashMap.put("grant_type" , "password")
            hashMap.put("username" , username.text.toString())
            hashMap.put("password" , password.text.toString())

            val call = NectarApplication.mRetroClient!!.login(hashMap)
            call.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    loading.visibility=View.GONE
                    Log.d("response", response.body().toString())
                    var rsp: JsonObject? = response.body() ?: return
                  //  Log.d("hjgugh", rsp.toString())
                    var user_id = rsp!!["user_id"]
                    var userName = rsp!!["userName"]
                    Log.d("user_id", user_id.toString())
                    access_token = rsp!!["access_token"].toString()
                  //  access_token = access_token!!.drop(1)
                    //access_token= access_token.replace("^0*".toRegex(), "") // -> 123
                    access_token=access_token.substring(1, access_token.length-1);
                    Log.d("access_token", access_token.toString())
                     NectarApplication.token=access_token

                    NectarApplication.userName=userName.toString()
                     //rememberme()
                    val sharedPreferences: SharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                 //   editor.putString(AppConstants.Username,username.text.toString())
                   // editor.putString(AppConstants.Password,password.text.toString())
                    if(checkbox1.isChecked)
                    {
                        editor.putString(AppConstants.Username,username.text.toString())
                        editor.putString(AppConstants.Password,password.text.toString())
                    }
                    else
                    {
                        editor.putString(AppConstants.Username,null)
                        editor.putString(AppConstants.Password,null)
                    }

                    editor.apply()
                    editor.commit()
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            })
        }
        }
    }

    private fun setvalueforrememberme() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val sharedNameValue = sharedPreferences.getString(AppConstants.Username,null)
        val sharedPasswordValue = sharedPreferences.getString(AppConstants.Password,null)

        if(sharedNameValue!=null&&sharedPasswordValue!=null)
        {

            checkbox1.isChecked= true
        }
        else
        {
            checkbox1.isChecked= false
        }
    }

         private fun rememberme() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()

        if(checkbox1.isChecked)
        {
            editor.putString(AppConstants.Username,username.text.toString())
            editor.putString(AppConstants.Password,password.text.toString())
        }
        else
        {
            editor.putString(AppConstants.Username,null)
            editor.putString(AppConstants.Password,null)
        }

        editor.apply()
        editor.commit()
    }
}