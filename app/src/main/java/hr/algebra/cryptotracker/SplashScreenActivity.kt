package hr.algebra.cryptotracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.algebra.cryptotracker.databinding.ActivitySplashScreenBinding
import hr.algebra.cryptotracker.framework.applyAnimation
import hr.algebra.cryptotracker.framework.callDelayed
import hr.algebra.cryptotracker.framework.startActivity

private const val DELAY = 3000L

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimation()
        callDelayed(DELAY) { redirect() }
    }

    private fun startAnimation() {
        binding.ivCoinImage.applyAnimation(R.anim.scale_y)
        binding.ivLineImage.applyAnimation(R.anim.scale_x)
    }

    private fun redirect() {
        startActivity<HostActivity>()
    }
}