package com.example.randomdraw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var cardImageURL = ""
    var cardNameURL = ""
    var cardTypeURL = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.cardButton)
        val imageView = findViewById<ImageView>(R.id.cardImage)
        val textView = findViewById<TextView>(R.id.cardName)
        val textView2 = findViewById<TextView>(R.id.cardType)

        getNextImage(button, imageView, textView, textView2)

        getCardImageURL()
        Log.d("cardImageURL", "card image URL set")

    }

    private fun getCardImageURL() {
        val client = AsyncHttpClient()

        client["https://db.ygoprodeck.com/api/v7/randomcard.php", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                /*cardImageURL = json.jsonObject.getString("card_images")*/
                var resultsJSON = json.jsonObject.getJSONArray("card_images").getJSONObject(0)
                cardImageURL = resultsJSON.getString("image_url")

                cardNameURL = json.jsonObject.getString("name")
                cardTypeURL = json.jsonObject.getString("type")


                Log.d("Card", "response successful$json")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Card Error", errorResponse)
            }
        }]
    }

    private fun getNextImage(button: Button, imageView: ImageView, textView: TextView, textView2: TextView) {

        button.setOnClickListener {

                getCardImageURL()

            Glide.with(this)
                . load(cardImageURL)
                .fitCenter()
                .into(imageView)

            textView.text = cardNameURL
            textView2.text = cardTypeURL
        }
    }

}

