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
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.lang.Math.abs


class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var bitmap: Bitmap
    private lateinit var btnSaveToHistory: Button
    private lateinit var btnHistory: Button

    private lateinit var sqliteHelper: SQLiteHelper

    private var std: HistoryModel? = null

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.btn_upload)
        imageView = findViewById(R.id.img_hasil)
        textView = findViewById(R.id.textView)
        btnSaveToHistory = findViewById(R.id.btnSaveToHistory)
        btnHistory = findViewById(R.id.btnHistory)
        sqliteHelper = SQLiteHelper(this)



        imageView.setImageResource(R.drawable.merah2)

        val drawable = imageView.drawable as BitmapDrawable
        bitmap = drawable.bitmap

        val colour = bitmap.getPixel(bitmap.getWidth()/2, bitmap.getHeight()/2)
        val red: Int = Color.red(colour)
        val blue: Int = Color.blue(colour)
        val green: Int = Color.green(colour)
        val alpha: Int = Color.alpha(colour)

        textView.text = ("R = $red, G = $green, B = $blue")


        button.setOnClickListener{
            uploadGallery()
        }
        btnHistory.setOnClickListener {
            intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        btnSaveToHistory.setOnClickListener { saveToHistory(4, 206, 255, 4) }

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

            val colour = bitmap.getPixel(bitmap.getWidth()/2, bitmap.getHeight()/2)
            val red: Int = Color.red(colour)
            val blue: Int = Color.blue(colour)
            val green: Int = Color.green(colour)
            //val alpha: Int = Color.alpha(colour)

            var hasil = algoritmaKKN(red, green, blue)
            var hasilAkhir: String = ""
            if (hasil < 7){
                hasilAkhir = ("pH = $hasil (Acidic/Asam)")
                textView.text = ("$hasilAkhir \nR = $red, G = $green, B = $blue")
            }
            if (hasil == 7){
                hasilAkhir = ("pH = $hasil (Neutral/Netral)")
                textView.text = ("$hasilAkhir \nR = $red, G = $green, B = $blue")
            }
            if (hasil > 7){
                hasilAkhir = ("pH = $hasil (Alkaline/Basa)")
                textView.text = ("$hasilAkhir \nR = $red, G = $green, B = $blue")
            }
            btnSaveToHistory.setOnClickListener { saveToHistory(hasil, red, green, blue) }

            //textView.text = ("R = $red, G = $green, B = $blue, A = $alpha")
        }
    }
    private fun saveToHistory(hasil: Int, red: Int, green: Int, blue: Int){
        val hasil_history = hasil
        val r_history = red
        val g_history = green
        val b_history = blue


        val std = HistoryModel(hasil_history = hasil_history, r_history = r_history, g_history = g_history, b_history = b_history)
        val status = sqliteHelper.insertHistory(std)

        if (status > -1){
            Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
        }
    }
    private fun algoritmaKKN(red: Int, green: Int, blue: Int): Int{
        var testDUji = arrayOf(
            arrayOf(0, 237, 28, 36),
            arrayOf(1, 255, 90, 54),
            arrayOf(2, 255, 153, 51),
            arrayOf(3, 255, 196, 12),
            arrayOf(4, 254, 254, 34),
            arrayOf(5, 154, 205, 50),
            arrayOf(6, 76, 187, 23),
            arrayOf(7, 0, 144, 0),
            arrayOf(8, 0, 158, 96),
            arrayOf(9, 10, 186, 181),
            arrayOf(10, 0, 135, 189),
            arrayOf(11, 15, 82, 186),
            arrayOf(12, 55, 36, 180),
            arrayOf(13, 69, 23, 183),
            arrayOf(14, 50, 18, 122)
        )

        var d = 0
        var minimum = 10000
        var indexMin = 0
        for (i in 0..14){
            d = abs(red - testDUji[i][1]) + abs(green - testDUji[i][2]) + abs(blue - testDUji[i][3])
            if (d < minimum){
                minimum = d
                indexMin = i
            }
        }
        return indexMin
    }




}