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
import hr.algebra.cryptotracker.framework.getStringPreference
import hr.algebra.cryptotracker.framework.setStringPreference
import hr.algebra.cryptotracker.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val LOGGED_USER = "hr.algebra.cryptotracker.logged_user"

class HostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHostBinding

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
                if(this.getStringPreference(LOGGED_USER)?.isEmpty() == true) {
                    findNavController(R.id.navigationController).navigate(R.id.action_to_LoginFragment)
                } else {
                    logout()
                }
                return true
            }
            R.id.menuExit -> {
                exitApp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.logout)
            setMessage(getString(R.string.are_you_sure))
            setIcon(R.drawable.exit_icon)
            setCancelable(true)
            setNegativeButton(getString(R.string.cancel), null)
            setPositiveButton("Ok") { _, _ -> this@HostActivity.setStringPreference(LOGGED_USER, "") }
            show()
        }
    }

    private fun exitApp() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.logout))
            setMessage(getString(R.string.are_you_sure))
            setIcon(R.drawable.exit_icon)
            setCancelable(true)
            setNegativeButton(getString(R.string.cancel), null)
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