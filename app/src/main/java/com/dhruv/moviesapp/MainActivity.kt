package com.dhruv.moviesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dhruv.moviesapp.databinding.ActivityMainBinding
import com.dhruv.moviesapp.fragments.PopularMoviesFragment
import com.dhruv.moviesapp.movie_details.MovieDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create instance of the ActivityMainBinding,
        // as we have only one layout activity_main.xml
        var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ArrayAdapter.createFromResource(this,R.array.fragments,android.R.layout.simple_spinner_dropdown_item)
            .also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.fragmentSpinner.adapter = adapter
                binding.fragmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        when(p2){
                            1-> gotoPopular()/*selectFragment(PopularMoviesFragment())*/
                            2-> gotoTopRated()
                            3-> gotoUpcoming()
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }


                }
            }


    }

    private fun selectFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction  = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.Movie_fragments, fragment).commit()

    }
    fun gotoPopular(){
        intent = Intent(this, PopularMoviesActivity::class.java)
        Toast.makeText(this@MainActivity, "GOING TO POPULAR Activity", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
    fun gotoTopRated(){
        intent = Intent(this, TRMovieActivity::class.java)
        Toast.makeText(this@MainActivity, "GOING TO TR Activity", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
    fun gotoUpcoming(){
        intent = Intent(this,  UpcomingMoviesActivity::class.java)
        Toast.makeText(this@MainActivity, "GOING TO UPCOMING Activity", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
}