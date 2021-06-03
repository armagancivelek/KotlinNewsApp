package com.androiddevs.mvvmnewsapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.HwAds
import com.huawei.hms.ads.banner.BannerView
import kotlinx.android.synthetic.main.activity_news.*

class NewActivity: AppCompatActivity() {



    lateinit var viewModel:NewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        /**
         * Advertisment block
         */

        supportActionBar?.hide()
        HwAds.init(this)
        val bottomBannerView = findViewById<BannerView>(R.id.hw_banner_view)
        val adParam = AdParam.Builder().build()


        val  newRepository=NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory=NewsViewModelProviderFactory(newRepository)
        viewModel=ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)

         val navController =newsNavHostFragment.findNavController()
          val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.breakingNewsFragment,
                R.id.savedNewsFragment,
                R.id.searchNewsFragment
            )
        )
         bottomNavigationView.visibility=View.GONE

        bottomNavigationView
            .setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfiguration)


        navController.addOnDestinationChangedListener {
            _,destination,_ ->
            if(destination.id == R.id.breakingNewsFragment)
            {
                bottomNavigationView.visibility=View.VISIBLE
                bottomBannerView.loadAd(adParam)
            }

            if(destination.id == R.id.searchNewsFragment)
            {
                bottomNavigationView.visibility=View.VISIBLE
                bottomBannerView.loadAd(adParam)
            }

            if(destination.id == R.id.savedNewsFragment)
            {
                bottomNavigationView.visibility=View.VISIBLE
                bottomBannerView.loadAd(adParam)
            }


        }



    }

    override fun onSupportNavigateUp(): Boolean {

         return findNavController(R.id.newsNavHostFragment).navigateUp()
    }

}
