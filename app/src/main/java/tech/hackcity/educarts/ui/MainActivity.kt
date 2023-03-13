package tech.hackcity.educarts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_EduCarts)
        setContentView(binding.root)

        //keep app on light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.myOrderFragment,
                R.id.paymentFragment, R.id.supportFragment,
                R.id.accountFragment
            )
        )

        //Toolbar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //bottom navigation bar
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnItemReselectedListener {
            return@setOnItemReselectedListener
        }
        binding.bottomNav.itemIconTintList = null

//        setupPaymentIcon()
    }

    private fun setupPaymentIcon() {
        //find the icon view for the menu item index 2
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        val menu = bottomNavigationView.menu
        val menuItem = menu.getItem(2)
        val navigationBarItemView: NavigationBarItemView = bottomNavigationView.findViewById(menuItem.itemId)
        val iconView: View = navigationBarItemView.findViewById(com.google.android.material.R.id.navigation_bar_item_icon_view)

        //set the new width and height for the iconView in pixels. You can change also the bottom margin of the icon View.
        val iconViewParams: FrameLayout.LayoutParams = iconView.layoutParams as FrameLayout.LayoutParams
        iconViewParams.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44F, resources.displayMetrics).toInt()
        iconViewParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 47F, resources.displayMetrics).toInt()
        iconViewParams.bottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35F, resources.displayMetrics).toInt()
        iconView.layoutParams = iconViewParams

        recursiveClipChildrenAndClipToPadding(bottomNavigationView)
    }

    private fun recursiveClipChildrenAndClipToPadding(parent: ViewGroup) {

        if(parent is BottomNavigationView){
            val bottomNavigationView = parent as BottomNavigationView
            bottomNavigationView.clipChildren = false
            bottomNavigationView.clipToPadding = false
        }
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child is ViewGroup) {
                val vGroup = child as ViewGroup
                vGroup.clipChildren = false
                vGroup.clipToPadding = false
                recursiveClipChildrenAndClipToPadding(vGroup)
            }
        }
    }
}