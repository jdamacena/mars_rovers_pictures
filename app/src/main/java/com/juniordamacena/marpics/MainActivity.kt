package com.juniordamacena.marpics

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.juniordamacena.marpics.databinding.ActivityMainBinding
import com.juniordamacena.marpics.models.Rover
import com.juniordamacena.marpics.services.RetrofitInstance
import com.juniordamacena.marpics.ui.main.SectionsPagerAdapter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
         val viewPager: ViewPager = binding.viewPager
         viewPager.adapter = sectionsPagerAdapter
         val tabs: TabLayout = binding.tabs
         tabs.setupWithViewPager(viewPager)*/
        val fab: FloatingActionButton = binding.fab

        runBlocking {
            launch {
                try {
                    val response = RetrofitInstance.api.listRovers(RetrofitInstance.API_KEY)
                    val listRovers: List<Rover> = response.body()?.rovers ?: emptyList()

                    val sectionsPagerAdapter = SectionsPagerAdapter(
                        this@MainActivity,
                        supportFragmentManager,
                        listRovers
                    )
                    val viewPager: ViewPager = binding.viewPager
                    viewPager.adapter = sectionsPagerAdapter
                    val tabs: TabLayout = binding.tabs
                    tabs.setupWithViewPager(viewPager)

                } catch (e: IOException) {
                    Log.e("MainActivity", e.message ?: "IOException")
                } catch (e: HttpException) {
                    Log.e("MainActivity", e.message ?: "HttpException")
                }
            }

            fab.setOnClickListener { view ->
                Snackbar
                    .make(view, "roverName", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
            }
        }
    }
}