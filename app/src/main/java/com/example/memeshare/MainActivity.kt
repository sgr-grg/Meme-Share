package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memeshare.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    var uri: String? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadMeme()
        binding.share.setOnClickListener {
            onShareClicked()
        }
        binding.next.setOnClickListener {
            loadMeme()
        }
    }

    private fun onShareClicked() {
        if (!uri.isNullOrBlank()) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share")
            intent.putExtra(Intent.EXTRA_TEXT, uri)
            startActivity(Intent.createChooser(intent, "Share Using"))

        } else {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadMeme() {
        binding.progress.visibility = View.VISIBLE
        val memes = RetrofitClientInstance.memeInstance.getMeme()
        memes.enqueue(object : Callback<Meme> {
            override fun onResponse(call: Call<Meme>, response: Response<Meme>) {
                runOnUiThread {
                    uri = response.body()?.url
                    Glide.with(this@MainActivity).load(uri).listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            binding.progress.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            binding.progress.visibility = View.GONE
                            return false
                        }
                    }).into(binding.image)
                }
            }

            override fun onFailure(call: Call<Meme>, t: Throwable) {
                Log.d("MEME", "Something Went Wrong")
            }
        })
    }
}