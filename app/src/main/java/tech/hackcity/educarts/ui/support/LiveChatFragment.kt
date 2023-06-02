package tech.hackcity.educarts.ui.support

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentLiveChatBinding

/**
 *Created by Victor Loveday on 5/19/23
 */
class LiveChatFragment: Fragment(R.layout.fragment_live_chat) {

    private lateinit var binding: FragmentLiveChatBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLiveChatBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}