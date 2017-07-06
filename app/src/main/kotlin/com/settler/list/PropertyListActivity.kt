package com.settler.list

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.settler.R
import com.settler.create.CreatePropertyActivity

class PropertyListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_list)

        setupFab()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_property_list, menu)
        return true
    }

    private fun setupFab() {
        val fab = findViewById<FloatingActionButton>(R.id.action_add) as FloatingActionButton

        fab.setOnClickListener { _ ->
            val intent = Intent(this, CreatePropertyActivity::class.java)
            startActivity(intent)
        }
    }

}
