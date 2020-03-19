package com.androdocs.populatelistview

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.squareup.picasso.Picasso
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.detail_activity.*


class ArticleDetailActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        val intent = getIntent();
        lateinit var toolbar: Toolbar
        lateinit var drawerLayout: DrawerLayout
        lateinit var navView: NavigationView
        val str = intent.getStringExtra("passdata");
        val images = intent.getStringExtra("passimg");
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer)
        navView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        val detailTextView = findViewById(R.id.article_row_name) as TextView
        findViewById(R.id.article_row_image) as ImageView
        detailTextView.setText(str)
        if (images!!.isEmpty()) {
            findViewById<ImageView>(R.id.article_row_image)
                .setImageResource(R.drawable.ic_launcher_foreground);
        } else {
            findViewById<ImageView>(R.id.article_row_image).visibility = View.VISIBLE;
            Picasso.get()
                .load("images")
                .resize(50, 50)
                .centerCrop()
                .into(findViewById<ImageView>(R.id.row_image))
        }
    }

    override fun onNavigationItemSelected(value: MenuItem): Boolean {
        when (value.itemId) {
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
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}