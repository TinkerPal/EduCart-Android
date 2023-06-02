package tech.hackcity.educarts.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import tech.hackcity.educarts.R
import tech.hackcity.educarts.domain.model.OnBoarding

/**
 *Created by Victor Loveday on 2/19/23
 */
class OnBoardingAdapter(
    private var context: Context,
    private var onBoardingDataList: List<OnBoarding>
) : PagerAdapter() {
    override fun getCount(): Int {
        return onBoardingDataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.on_boarding_item, null)

        val illustrationImage: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.title)
        val description: TextView = view.findViewById(R.id.description)

        illustrationImage.setImageResource(onBoardingDataList[position].image)
        title.text = onBoardingDataList[position].title
        description.text = onBoardingDataList[position].desc

        container.addView(view)
        return view
    }
}
