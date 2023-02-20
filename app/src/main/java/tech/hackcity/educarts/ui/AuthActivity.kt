package tech.hackcity.educarts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import tech.hackcity.educarts.databinding.ActivityAuthBinding
import tech.hackcity.educarts.ui.viewmodels.SharedViewModel

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize viewModel(s)
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        setupToolbar()

    }

    private fun setupToolbar() {
        supportActionBar?.title = ""

        sharedViewModel.isToolbarVisible().observe(this) {isToolbarVisible ->
            if (!isToolbarVisible) {
                supportActionBar?.hide()
            }
        }

    }
}