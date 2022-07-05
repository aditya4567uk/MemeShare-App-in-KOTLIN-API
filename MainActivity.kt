package com.example.memeshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentimageurl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadmeme()
    }

    fun loadmeme()
    {
        //  Initiate the request queue
        progressBar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // request a string response from  the provider URL
        val jsonobjectrequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener{ response ->
                val currentimageurl = response.getString("url")
                Glide.with(this).load(currentimageurl).into(memeimageview)
                progressBar.visibility = View.GONE
            },
            Response.ErrorListener{
                progressBar.visibility = View.VISIBLE
                Toast.makeText(this,"something went wrong", Toast.LENGTH_LONG).show()
            })

        queue.add(jsonobjectrequest)
    }

    fun sharememe(view: View)
    {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey check out this meme i found $currentimageurl")
        val chooser = Intent.createChooser(intent,"Share this meme using....")
        startActivity(chooser)
    }
    fun nextmeme(view: View)
    {
        loadmeme()
    }
}