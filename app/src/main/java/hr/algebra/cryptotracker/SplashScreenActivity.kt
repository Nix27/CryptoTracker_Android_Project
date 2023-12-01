package hr.algebra.cryptotracker

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import hr.algebra.cryptotracker.databinding.ActivitySplashScreenBinding
import hr.algebra.cryptotracker.framework.applyAnimation
import hr.algebra.cryptotracker.framework.startActivity
import java.util.Objects

private const val DELAY = 3000L

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimation()
        redirect()
    }

    private fun startAnimation() {
        binding.ivCoinImage.applyAnimation(R.anim.scale_y)
        binding.ivLineImage.applyAnimation(R.anim.scale_x)
    }

    private fun redirect() {
        Handler(Looper.getMainLooper()).postDelayed(
            { startActivity<HostActivity>() },
            DELAY
        )
    }
}