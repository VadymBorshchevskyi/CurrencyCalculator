package ai.codetest.currencycalculator.ui

import ai.codetest.currencycalculator.R
import ai.codetest.currencycalculator.databinding.MainActivityBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding
    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.main_activity
        )

        binding.rootTabs.setupWithNavController(navController)
    }
}