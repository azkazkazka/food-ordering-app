package com.example.majika

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.example.majika.databinding.ActivityMainBinding
import com.example.majika.model.MenuModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    public var menuModel : ArrayList<MenuModel> = ArrayList<MenuModel>()
    public var updateMenuList: ArrayList<MenuModel> = ArrayList<MenuModel>()

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Menu())
        // select the active navigation
        binding.bottomNavigationView.selectedItemId = R.id.menu

        binding.bottomNavigationView.setOnItemSelectedListener {
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