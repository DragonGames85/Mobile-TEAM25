package com.example.team25
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.team25.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var bindi: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindi = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindi.root)
        bindi.bNav?.selectedItemId = R.id.item3
        bindi.bNav?.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.item1 -> {
                    Toast.makeText(this, "launched", Toast.LENGTH_SHORT).show()
                }
                R.id.item2 -> {
                    Toast.makeText(this, "launched", Toast.LENGTH_SHORT).show()
                }
                R.id.item3 -> {
                    Toast.makeText(this, "launched", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.launch -> {
                Toast.makeText(this, "launched", Toast.LENGTH_SHORT).show()

            }
            R.id.clear -> {
                Toast.makeText(this, "cleared", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

//    fun Launch(view : View) {
//        var tes=findViewById<TextView>(R.id.Textr)
//        tes.text =  "JO"
//    }
}