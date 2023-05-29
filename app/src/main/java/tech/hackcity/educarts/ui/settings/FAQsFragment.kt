package tech.hackcity.educarts.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.ui.adapters.FAQsAdapter
import tech.hackcity.educarts.domain.model.FAQ
import tech.hackcity.educarts.databinding.FragmentFaqsBinding

/**
 *Created by Victor Loveday on 2/26/23
 */
class FAQsFragment: Fragment(R.layout.fragment_faqs) {
    private lateinit var binding: FragmentFaqsBinding

    private lateinit var faqsAdapter: FAQsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFaqsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val faq1 = FAQ(0, "Who designed EduCarts mobile app?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ")
        val faq2 = FAQ(1, "Who designed EduCarts web app?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ")
        val faq3 = FAQ(2, "Who designed EduCarts backend?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ")
        val faq4 = FAQ(3, "Who designed EduCarts frontend?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ")
        val faq5 = FAQ(4, "Who designed EduCarts architecture?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ")
        val faq6 = FAQ(5, "Who designed EduCarts logo?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ")
        val faq7 = FAQ(6, "Who designed EduCarts business model?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ")
        val faq8 = FAQ(7, "Who designed EduCarts?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ")
        val faq:List<FAQ> = arrayListOf(faq1, faq2, faq3, faq3, faq4, faq5, faq6, faq7)

        faqsAdapter = FAQsAdapter(requireContext())
        binding.faqsRV.apply {
            adapter = faqsAdapter
            layoutManager = LinearLayoutManager(requireContext())

            faqsAdapter.setData(faq)

            faqsAdapter.setOnItemClickListener {

            }
        }
    }
}