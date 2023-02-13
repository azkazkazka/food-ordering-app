package com.example.majika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.example.majika.databinding.ActivityMainBinding
import com.example.majika.model.MenuModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    public var menuModel : ArrayList<MenuModel> = ArrayList<MenuModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Menu())
        binding.bottomNavigationView.selectedItemId = R.id.menu

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu -> {
                    replaceFragment(Menu())
                    true
                }
                R.id.twibbon -> {
                    replaceFragment(Twibbon())
                    true
                }
                R.id.location -> {
                    replaceFragment(Location())
                    true
                }
                R.id.cart -> {
                    replaceFragment(Cart())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}