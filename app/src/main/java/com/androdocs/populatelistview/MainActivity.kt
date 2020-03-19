package com.androdocs.populatelistview

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject
import androidx.appcompat.widget.Toolbar

import java.net.URL

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.Kotlin_news -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_update -> {
                Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    var dataList = ArrayList<HashMap<String, String>>()

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.listView)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                // Get the selected item text from ListView
                val selectedItem = dataList.get(position)
                intent = Intent(this, ArticleDetailActivity::class.java)
                intent.putExtra("passdata", selectedItem.get("title"))
                intent.putExtra("passimg", selectedItem.get("thumbnail"))
                startActivity(intent)
                // Display the selected item text on TextView

            }
        fetchJsonData().execute()
    }


    inner class fetchJsonData() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): String? {
            return URL("https://www.reddit.com/r/kotlin/.json").readText(
                Charsets.UTF_8
            )
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE

            val jsonObj = JSONObject(result)
            val jb = jsonObj.getJSONObject("data")
            val childarr = jb.getJSONArray("children")

            for (i in 0 until childarr.length()) {
                val singleUser = childarr.getJSONObject(i)
                val childdata = singleUser.getJSONObject("data")
                val map = HashMap<String, String>()
                map["title"] = childdata.get("title") as String
                map["thumbnail"] = childdata.get("thumbnail") as String
                dataList.add(map)
            }

            findViewById<ListView>(R.id.listView).adapter =
                CustomAdapter(this@MainActivity, dataList)
        }
    }


}
