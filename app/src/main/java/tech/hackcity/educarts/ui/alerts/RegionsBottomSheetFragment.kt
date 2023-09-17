package tech.hackcity.educarts.ui.alerts

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.RegionsBottomSheetBinding

/**
 *Created by Victor Loveday on 9/15/23
 */


class RegionsBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: RegionsBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegionsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        val layout: CoordinatorLayout? = dialog?.findViewById(R.id.regionBottomSheetLayout)
        layout?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels

        binding.closeRegionBottomSheetBtn.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        // Notify the listener when the binding is ready
        bindingReadyCallback(binding)
    }

    // Define a function to set up a listener for binding readiness
    private lateinit var bindingReadyCallback: (RegionsBottomSheetBinding) -> Unit

    fun onBindingReady(callback: (RegionsBottomSheetBinding) -> Unit) {
        bindingReadyCallback = callback
    }

    fun closeBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//class RegionsBottomSheetFragment : BottomSheetDialogFragment() {
//    lateinit var binding: RegionsBottomSheetBinding
//    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = RegionsBottomSheetBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//
//        val layout: CoordinatorLayout? = dialog?.findViewById(R.id.regionBottomSheetLayout)
//        layout?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
//
//        binding.closeRegionBottomSheetBtn.setOnClickListener {
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//        }
//    }
//}


