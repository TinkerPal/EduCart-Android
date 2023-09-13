package tech.hackcity.educarts.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import tech.hackcity.educarts.R
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.repositories.MainRepository
import tech.hackcity.educarts.data.storage.SessionManager
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.data.storage.UserInfoManager
import tech.hackcity.educarts.databinding.ActivityMainBinding
import tech.hackcity.educarts.domain.model.settings.ProfileResponse
import tech.hackcity.educarts.ui.alerts.ToastType
import tech.hackcity.educarts.uitls.toast

class MainActivity : AppCompatActivity(), MainListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.paymentFragment, R.id.supportFragment,
                R.id.accountFragment
            )
        )

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        //Toolbar
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //bottom navigation bar
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnItemReselectedListener {
            return@setOnItemReselectedListener
        }
        binding.bottomNav.itemIconTintList = null

        val api = RetrofitInstance(this)
        val sessionManager = SessionManager(this)
        val sharePreferencesManager = SharePreferencesManager(this)
        val userInfoManager = UserInfoManager(this)
        val repository = MainRepository(api, sessionManager, sharePreferencesManager, userInfoManager)
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.listener = this

//        Coroutines.onMainWithScope(viewModel.viewModelScope) {
//            viewModel.fetchProfile()
//        }
    }

    override fun onFetchProfileRequestStarted() {

    }

    override fun onRequestFailed(message: String) {
        toast(description = message, toastType = ToastType.ERROR)
        Log.d("MainActivity", message)
    }

    override fun onFetchProfileRequestSuccessful(response: ProfileResponse) {

    }

    override fun onResume() {
        super.onResume()
//        Coroutines.onMainWithScope(viewModel.viewModelScope) {
//            viewModel.fetchProfile()
//        }
    }
}