package com.example.volleyapi_integration

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.volleyapi_integration.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val url: String = "https://meme-api.com/gimme"
    lateinit var binding: ActivityMainBinding

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

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.e("Response","getMemeData: " + response.toString())

                var responseObject = JSONObject(response)



                responseObject.get("postLink")
                responseObject.get("author")

                binding.memeTitle.text = responseObject.getString("title")
                binding.memeAuthor.text = responseObject.getString("author")
//                binding.memeImage
                Glide.with(this).load(responseObject.get("url")).into(binding.memeImage)
                progressDialog.dismiss()
            },
            { error ->
                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            })

        queue.add(stringRequest)
    }
}