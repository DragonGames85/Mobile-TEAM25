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

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bNav?.selectedItemId = R.id.item3
        binding.bNav?.setOnItemSelectedListener {
            when(it.itemId) {

                R.id.item1 -> {

                }

                R.id.item2 -> {

                }

                R.id.item3 -> {

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