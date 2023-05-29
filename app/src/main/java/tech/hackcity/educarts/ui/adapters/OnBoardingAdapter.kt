package tech.hackcity.educarts.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.OnBoardingItemBinding
import tech.hackcity.educarts.domain.model.OnBoarding

/**
 *Created by Victor Loveday on 2/19/23
 */
class OnBoardingAdapter(
    private var context: Context,
    private var onBoarding: List<OnBoarding>
) : PagerAdapter() {
    override fun getCount(): Int {
        return onBoarding.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = OnBoardingItemBinding.inflate(LayoutInflater.from(context), container, false)
        val view = binding.root

        Glide.with(context)
            .load(onBoarding[position].image)
            .into(binding.image)

        binding.title.text = onBoarding[position].title
        binding.description.text = onBoarding[position].desc

//        binding.imageCardView.setBackgroundResource(R.drawable.intro_sreen_image_place_holder)

        //add view to container
        container.addView(view)

        return view
    }
}
