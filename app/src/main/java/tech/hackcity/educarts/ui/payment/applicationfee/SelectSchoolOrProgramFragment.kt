package tech.hackcity.educarts.ui.payment.applicationfee

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSelectSchoolOrProgramBinding
import tech.hackcity.educarts.ui.adapters.ProgramsAdapter
import tech.hackcity.educarts.ui.adapters.SchoolsAdapter
import tech.hackcity.educarts.uitls.Constants

/**
 *Created by Victor Loveday on 5/31/23
 */
class SelectSchoolOrProgramFragment : Fragment(R.layout.fragment_select_school_or_program) {

    private lateinit var binding: FragmentSelectSchoolOrProgramBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSelectSchoolOrProgramBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupSchoolRecyclerView()

        binding.tabs.setOnCheckedChangeListener { group, selectedTb ->
            when (selectedTb) {
                R.id.tab1 -> {
                    setupSchoolRecyclerView()
                    binding.tab1.apply {
                        setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                    }
                    binding.tab2.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.text_light
                        )
                    )

                }
                R.id.tab2 -> {
                    setupProgramRecyclerView()
                    binding.tab2.apply {
                        setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                    }
                    binding.tab1.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.text_light
                        )
                    )
                }
            }

        }


    }

    private fun setupSchoolRecyclerView() {
        val schoolsAdapter = SchoolsAdapter(requireContext())
        binding.schoolOrProgramRV.apply {
            adapter = schoolsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            schoolsAdapter.setData(Constants.dummySchoolList)
        }

        schoolsAdapter.setOnItemClickListener {
            val action =
                SelectSchoolOrProgramFragmentDirections.actionSelectSchoolOrProgramFragmentToSchoolInformationFragment(
                    it
                )
            findNavController().navigate(action)
        }
    }

    private fun setupProgramRecyclerView() {
        val schoolsAdapter = ProgramsAdapter(requireContext())
        binding.schoolOrProgramRV.apply {
            adapter = schoolsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            schoolsAdapter.setData(Constants.dummyProgramList)
        }

        schoolsAdapter.setOnItemClickListener {
            val action =
                SelectSchoolOrProgramFragmentDirections.actionSelectSchoolOrProgramFragmentToProgramInformationFragment(
                    it
                )
            findNavController().navigate(action)
        }

    }
}