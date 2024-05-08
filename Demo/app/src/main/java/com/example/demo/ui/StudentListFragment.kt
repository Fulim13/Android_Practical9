package com.example.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.demo.R
import com.example.demo.data.StudentVM
import com.example.demo.databinding.FragmentStudentListBinding
import com.example.demo.util.StudentAdapter

class StudentListFragment : Fragment() {

    private lateinit var binding: FragmentStudentListBinding
    private val nav by lazy { findNavController() }

    private val vm: StudentVM by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStudentListBinding.inflate(inflater, container, false)

        // -----------------------------------------------------------------------------------------

        binding.btnRestore.setOnClickListener { vm.restore() }
        binding.btnDeleteAll.setOnClickListener { vm.deleteAll() }
        binding.btnInsert.setOnClickListener { nav.navigate(R.id.studentInsertFragment) }

        // -----------------------------------------------------------------------------------------

        val adapter = StudentAdapter { h, s ->
            h.binding.btnUpdate.setOnClickListener { update(s.id) }
            h.binding.btnDelete.setOnClickListener { vm.delete(s) }
        }
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.getLiveData().observe(viewLifecycleOwner) {
            binding.txtCount.text = "${it.size} Record(s)"
            adapter.submitList(it)
        }

        // -----------------------------------------------------------------------------------------

        return binding.root
    }

    private fun update(studentId: Int) {
        nav.navigate(R.id.studentUpdateFragment, bundleOf(
            "studentId" to studentId
        ))
    }

}