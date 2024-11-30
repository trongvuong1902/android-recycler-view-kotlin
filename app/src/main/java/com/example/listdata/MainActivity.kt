package com.example.listdata

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listdata.ui.theme.ListDataTheme
import kotlinx.serialization.json.Json


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val dataItems = loadItemsFromJson(this)

        // Set up the adapter with the parsed items
        val adapter = DataItemAdapter(dataItems)

        recyclerView.adapter = adapter

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column { Text(text = "hello") }
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

fun loadItemsFromJson(context: Context): List<DataItem> {
    val resourceId = context.resources.getIdentifier("sample_data_list", "raw", context.packageName)
    val inputStream = context.resources.openRawResource(resourceId)
    val jsonString = inputStream.bufferedReader().use { it.readText() }
    val json = Json { ignoreUnknownKeys = true }
    val dataItems = json.decodeFromString<List<DataItem>>(jsonString)
    return dataItems
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListDataTheme {
        Greeting("Android")
    }
}