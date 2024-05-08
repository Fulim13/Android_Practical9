package com.example.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.demo.data.Program
import com.example.demo.data.ProgramVM
import com.example.demo.databinding.FragmentProgramListBinding
import com.example.demo.util.ProgramAdapter
import com.example.demo.util.infoDialog
import kotlinx.coroutines.launch

class ProgramListFragment : Fragment() {

    private lateinit var binding: FragmentProgramListBinding
    private val nav by lazy { findNavController() }

    private val vm: ProgramVM by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProgramListBinding.inflate(inflater, container, false)

        // -----------------------------------------------------------------------------------------

        binding.btnRestore.setOnClickListener { vm.restore() }
        binding.btnDeleteAll.setOnClickListener { vm.deleteAll() }
        binding.btnDemo1.setOnClickListener { demo1() }
        binding.btnDemo2.setOnClickListener { demo2() }

        // -----------------------------------------------------------------------------------------

        val adapter = ProgramAdapter { h, p ->
            h.binding.btnDelete.setOnClickListener { vm.delete(p) }
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

    private fun demo1() {
        // TODO(9): Read 1 program (RIS)
        lifecycleScope.launch{
            val p = vm.get("RIS")
            if (p == null) return@launch
            val s = "${p.id} - ${p.name}"
            infoDialog(s)
        }
    }

    private fun demo2() {
        // TODO(10): Read all programs
        lifecycleScope.launch{
            val programs = vm.getAll()
            val s = programs.joinToString("\n") { "${it.id} - ${it.name}" }
            infoDialog(s)
        }

    }

}