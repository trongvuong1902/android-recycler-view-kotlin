package com.example.listdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge


class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        val item =    intent.getParcelableExtra<Detail>("detail", )
        item?.let {
            findViewById<TextView>(R.id.titleText).text = it.title
            findViewById<TextView>(R.id.dateText).text = it.date
            findViewById<TextView>(R.id.descriptionText).text = it.description
        }

        findViewById<Button>(R.id.backBtn).setOnClickListener{
            finish();
        }

        findViewById<Button>(R.id.deleteBtn).setOnClickListener {
            val intent = Intent()
            intent.putExtra("detail", item)
            setResult(Activity.RESULT_OK, intent)
            finish() // Close the activity
        }

    }
}

