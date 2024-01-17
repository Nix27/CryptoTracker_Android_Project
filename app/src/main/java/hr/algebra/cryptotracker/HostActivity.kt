package hr.algebra.cryptotracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import hr.algebra.cryptotracker.api.CryptoFetcher
import hr.algebra.cryptotracker.databinding.ActivityHostBinding
import hr.algebra.cryptotracker.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHostBinding
    private lateinit var currencies: List<Currency>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )

        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initHamburgerMenu()
        initNavigation()
    }

    private fun initHamburgerMenu() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_icon)
    }

    private fun initNavigation() {
        val navController = Navigation.findNavController(this, R.id.navigationController)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.host_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                toggleDrawer()
                return true
            }
            R.id.menuUser -> {
                findNavController(R.id.navigationController).navigate(R.id.action_CurrenciesFragment_to_LoginFragment)
                return true
            }
            R.id.menuExit -> {
                exitApp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exitApp() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.exit)
            setMessage(getString(R.string.are_you_sure))
            setIcon(R.drawable.exit_icon)
            setCancelable(true)
            setNegativeButton("Cancel", null)
            setPositiveButton("Ok") { _, _ -> finish() }
            show()
        }
    }

    private fun toggleDrawer() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers()
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}