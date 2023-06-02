package tech.hackcity.educarts.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityGetStartedBinding
import tech.hackcity.educarts.ui.auth.AuthActivity

class GetStartedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        binding.personalAccountBtn.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            intent.putExtra("destination", "create personal account")
            startActivity(intent)
        }


        binding.signInText.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            intent.putExtra("destination", "login")
            startActivity(intent)
        }
    }
}