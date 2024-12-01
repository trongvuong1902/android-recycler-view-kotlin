package com.example.listdata
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.json.Json


class MainActivity : ComponentActivity() {
    private  lateinit var recyclerView: RecyclerView
    private  lateinit var dataItems:  MutableList<DataItem>
    private lateinit var deleteLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        dataItems = loadItemsFromJson(this).toMutableList()

        // Set up the adapter with the parsed items
        val adapter = DataItemAdapter(dataItems)

        recyclerView.adapter = adapter
        val sortButton = findViewById<Button>(R.id.sortButton)
        "SortBy".also { sortButton.text = it }

        sortButton.setOnClickListener {
            showSortDialog(this, adapter, dataItems)
        }

        adapter.onItemClick = {it
            val intent = Intent (this, DetailActivity::class.java).apply {
                val detail = Detail(it.index,it.title, it.date, it.description)
                putExtra("detail",detail)
            }
            deleteLauncher.launch(intent)
        }

        deleteLauncher =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val deletedItem = result.data?.getParcelableExtra<Detail>("detail")
                    if (deletedItem != null) {
                        // Find and remove the item from the list
                        val itemIndex = dataItems.indexOfFirst { it.index == deletedItem.index}
                        if (itemIndex != -1) {
                            dataItems.removeAt(itemIndex)
                            adapter.notifyItemRemoved(itemIndex)
                        }
                    }
                }
            }


    }
}


private fun showSortDialog(context: Context,  adapter: DataItemAdapter,  items:MutableList<DataItem> )  {
    val layoutInflater = LayoutInflater.from(context)
    val dialogView = layoutInflater.inflate(R.layout.sort_options, null)
    val radioGroup = dialogView.findViewById<RadioGroup>(R.id.sortOptionsGroup)
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder
        .setMessage("Please select option sort (DESC)")
        .setView(dialogView)

    val dialog: AlertDialog = builder.create()
    dialogView.findViewById<Button>(R.id.applySortButton).setOnClickListener {
        val selectedOption = when (radioGroup.checkedRadioButtonId) {
            R.id.sortByIndex -> "Index"
            R.id.sortByTitle -> "Title"
            R.id.sortByDate -> "Date"
            else -> null
        }

        if (selectedOption != null) {
            when(selectedOption) {
                "Index" -> {
                    adapter.updateList(items.sortedByDescending { it.index })
                }
                "Title" -> {
                    adapter.updateList(items.sortedByDescending  { it.title })
                }
                "Date" -> {
                    adapter.updateList(items.sortedByDescending  { it.date })
                }
            }

            dialog.dismiss()

        }
    }
    dialog.show()

}

fun loadItemsFromJson(context: Context): List<DataItem> {
    val resourceId = context.resources.getIdentifier("sample_data_list", "raw", context.packageName)
    val inputStream = context.resources.openRawResource(resourceId)
    val jsonString = inputStream.bufferedReader().use { it.readText() }
    val json = Json { ignoreUnknownKeys = true }
    val dataItems = json.decodeFromString<List<DataItem>>(jsonString)
    return dataItems
}

