package github.luthfipun.midtransandroidsample.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import github.luthfipun.midtransandroidsample.R
import github.luthfipun.midtransandroidsample.databinding.ActivityMainBinding
import github.luthfipun.midtransandroidsample.ui.fragment.product.ProductFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPhonePermission()
        initView()
    }

    private fun initView() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        binding.bottomNavigation.setOnNavigationItemReselectedListener(this)
    }

    private fun checkPhonePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 101)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.nav_product -> {
                replaceContainer(
                    ProductFragment.newInstance(),
                    item.title.toString()
                )
                true
            }
            R.id.nav_order -> {
                true
            }
            else -> false
        }
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        when(item.itemId){
            R.id.nav_product -> {
                replaceContainer(
                    ProductFragment.newInstance(),
                    item.title.toString()
                )
            }
        }
    }

    private fun replaceContainer(fragment: Fragment, title: String){
        binding.toolbar.title = title
        supportFragmentManager.commit {
            replace(binding.container.id, fragment)
        }
    }
}