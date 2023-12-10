package hr.algebra.cryptotracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.algebra.cryptotracker.api.CryptoWorker
import hr.algebra.cryptotracker.databinding.ActivitySplashScreenBinding
import hr.algebra.cryptotracker.framework.applyAnimation
import hr.algebra.cryptotracker.framework.callDelayed
import hr.algebra.cryptotracker.framework.isOnline

private const val DELAY = 3000L

const val DATA_IMPORTED = "hr.algebra.cryptotracker.data_imported"
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        startAnimation()
        callDelayed(DELAY) { redirect() }
    }

    private fun setupListeners() {
        binding.btnRetry.setOnClickListener {
            startAnimation()
            callDelayed(DELAY) { redirect() }
        }
    }

    private fun startAnimation() {
        if(binding.flAnimatedImages.visibility == View.GONE){
            binding.llNoInternetConnection.visibility = View.GONE
            binding.flAnimatedImages.visibility = View.VISIBLE
        }

        binding.ivCoinImage.applyAnimation(R.anim.scale_y)
        binding.ivLineImage.applyAnimation(R.anim.scale_x)
    }

    private fun redirect() {
        /*if (getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) { startActivity<HostActivity>() }
        } else {
            if(isOnline()){
                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.from(CryptoWorker::class.java)
                    )
                }
            } else {
                binding.flAnimatedImages.visibility = View.GONE
                binding.ivNoInternetImage.visibility = View.VISIBLE
                callDelayed(DELAY) { finish() }
            }
        }*/

        if(isOnline()){
            WorkManager.getInstance(this).apply {
                enqueueUniqueWork(
                    DATA_IMPORTED,
                    ExistingWorkPolicy.KEEP,
                    OneTimeWorkRequest.from(CryptoWorker::class.java)
                )
            }
        } else {
            binding.flAnimatedImages.visibility = View.GONE
            binding.llNoInternetConnection.visibility = View.VISIBLE
        }
    }
}