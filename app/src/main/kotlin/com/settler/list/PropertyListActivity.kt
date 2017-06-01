package com.settler.list

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.settler.R
import com.settler.create.NewPropertyActivity

class PropertyListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_list)

//        setupToolbar()
        setupFab()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_property_list, menu)
        return true
    }

    private fun setupFab() {
        val fab = findViewById(R.id.action_add) as FloatingActionButton

        fab.setOnClickListener { _ ->
            val intent = Intent(this, NewPropertyActivity::class.java)
            startActivity(intent)
        }
    }

    //    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        val id = item.itemId
//
//        if (id == R.id.action_settings) {
//            return true
//        }
//


//        return super.onOptionsItemSelected(item)

//    }

//    private fun setupToolbar() {
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
//        setSupportActionBar(toolbar)
//    }
}
