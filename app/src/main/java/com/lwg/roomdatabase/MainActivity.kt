package com.lwg.roomdatabase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lwg.roomdatabase.repository.ApiRepository
import com.lwg.roomdatabase.service.ApiRequest
import com.lwg.roomdatabase.viewModel.ApiModelFactory
import com.lwg.roomdatabase.viewModel.ApiViewModel


class MainActivity : AppCompatActivity(), WordListAdapter.OnClickItemAdapter {

    private val newWordActivityRequestCode = 1
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }

    private lateinit var apiViewModel: ApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService = ApiRequest.getInstance()
        val apiRepository = ApiRepository(apiService)
        apiViewModel =
            ViewModelProvider(this, ApiModelFactory(apiRepository))[ApiViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        wordViewModel.allWords.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }

        apiViewModel.getResultTracksCall()
        apiViewModel.movieList.observe(this) {
            it.forEach { it ->
                println("The element is ${it.name}")
            }
        }
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newWordActivityRequestCode && resultCode == RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let { reply ->
                val word = Word(0, reply)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onClickItem(v: View?) {
        val item = v?.tag as Word
        Log.d("lwg", "Word \nName: " + item.word + "\nId: " + item.id)
    }
}