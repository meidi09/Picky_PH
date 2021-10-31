package com.example.pickyph

import android.R.attr
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.Touch
import android.text.method.Touch.onTouchEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.graphics.drawable.BitmapDrawable
import android.R.attr.y

import android.R.attr.x
import android.graphics.Color
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var bitmap: Bitmap

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.btn_upload)
        imageView = findViewById(R.id.img_hasil)
        textView = findViewById(R.id.textView)

        imageView.setImageResource(R.drawable.merah2)

        val drawable = imageView.drawable as BitmapDrawable
        bitmap = drawable.bitmap

        val colour = bitmap.getPixel(100, 100)
        val red: Int = Color.red(colour)
        val blue: Int = Color.blue(colour)
        val green: Int = Color.green(colour)
        //val alpha: Int = Color.alpha(colour)

        textView.text = ("R = $red, G = $green, B = $blue")


        /*

        imageView.setImageDrawable(true)
        imageView.buildDrawingCache(true)

        imageView.setOnTouchListener(View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> bitmap = imageView.getDrawingCache()
                    var pixel = bitmap.getPixel((int)motionEvent.getX(),(int)motionEvent.getY())
                }

                return v?.onTouchEvent(event) ?: true
            }
        })

         */



        button.setOnClickListener{
            uploadGallery()
        }
    }

    private fun uploadGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            imageView.setImageURI(data?.data)
            val drawable = imageView.drawable as BitmapDrawable
            bitmap = drawable.bitmap

            val colour = bitmap.getPixel(100, 100)
            val red: Int = Color.red(colour)
            val blue: Int = Color.blue(colour)
            val green: Int = Color.green(colour)
            //val alpha: Int = Color.alpha(colour)

            textView.text = ("R = $red, G = $green, B = $blue")
        }
    }




}