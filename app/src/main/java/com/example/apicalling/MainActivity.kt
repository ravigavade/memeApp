package com.example.apicalling

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.apicalling.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val url: String = "https://meme-api.com/gimme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMemeData()

        binding.btnNewMeme.setOnClickListener {
            getMemeData()
        }
    }

    private fun getMemeData() {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.e("Response", "getMemeData: $response")

                val responseObject = JSONObject(response)
                binding.memeTitle.text = responseObject.getString("title")
                binding.memeAuthor.text = responseObject.getString("author")
                Glide.with(this).load(responseObject.getString("url")).into(binding.memeImage)
            },
            { error ->
                Toast.makeText(this@MainActivity, error.localizedMessage, Toast.LENGTH_SHORT).show()
            })
        queue.add(stringRequest)
    }
}
