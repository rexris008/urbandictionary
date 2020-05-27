package com.example.m.urbandictionary

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper.HORIZONTAL
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray


@Suppress("NAME_SHADOWING")
class SearchResultsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    var dataListArray = ArrayList<DataHelperClass>()
    var originalDataListArray = ArrayList<DataHelperClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setTitle(intent.getStringExtra(SearchManager.QUERY))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onNewIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {

            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
                        .saveRecentQuery(query, null)
                val query = intent.getStringExtra(SearchManager.QUERY)
                recyclerView = findViewById<RecyclerView>(R.id.rv)
                getJsonData(query)

            }
        }
    }


    fun getJsonData(query: String): Boolean {
        var dataObj: DataHelperClass
        val queue = Volley.newRequestQueue(this)
        val url: String = resources.getString(R.string.api_url)
        val pb = findViewById<ProgressBar>(R.id.pb)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url + "?term=" + query, null,

                Response.Listener { response ->
                    Log.i("cat", " the response is" + response)
                    val listJsonArray: JSONArray = response.getJSONArray("list")
                    var i = 0
                    while (i < listJsonArray.length()) {
                        dataObj = DataHelperClass(listJsonArray.getJSONObject(i).getString("definition"), listJsonArray.getJSONObject(i).getString("thumbs_up"), listJsonArray.getJSONObject(i).getString("thumbs_down"))
                        dataListArray.add(dataObj)
                        i++;
                    }

                    originalDataListArray = dataListArray
                    sortByUpVote()
                    recyclerView.adapter.apply {
                        viewManager = LinearLayoutManager(this@SearchResultsActivity)
                        recyclerView.layoutManager = viewManager
                        val itemDecor = ContextCompat.getDrawable(this@SearchResultsActivity,R.drawable.divider_drawable)?.let { DividerItemDecoration(it) }
                        recyclerView.addItemDecoration(itemDecor!!) // safe to assume non null and use non null asserted operator
                        pb.visibility = View.GONE
                        recyclerView.adapter = MyAdapter(originalDataListArray, this@SearchResultsActivity)
                        recyclerView.visibility = View.VISIBLE;
                    }
                },
                Response.ErrorListener { _ ->
                    pb.visibility = View.GONE
                    Toast.makeText(this@SearchResultsActivity, getString(R.string.error_msg), Toast.LENGTH_SHORT).show()
                }
        )
        queue.add(jsonObjectRequest)
        return true
    }

    fun sortByUpVote(){
        dataListArray.sortWith(Comparator { o1, o2 -> o1.thumbsUpCount .compareTo(o2.thumbsUpCount) })
    }

    fun sortByDownVote(){


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
