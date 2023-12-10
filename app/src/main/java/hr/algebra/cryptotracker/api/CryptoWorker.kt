package hr.algebra.cryptotracker.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class CryptoWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        CryptoFetcher(context).fetchCryptoCurrencies(10)
        return Result.success()
    }
}