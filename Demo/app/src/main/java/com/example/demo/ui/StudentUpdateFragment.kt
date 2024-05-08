package com.example.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.demo.data.Program
import com.example.demo.data.ProgramVM
import com.example.demo.data.Student
import com.example.demo.data.StudentVM
import com.example.demo.databinding.FragmentStudentUpdateBinding
import com.example.demo.util.errorDialog
import com.example.demo.util.getArrayAdapter
import com.example.demo.util.setSelection
import kotlinx.coroutines.launch

class StudentUpdateFragment : Fragment() {

    private lateinit var binding: FragmentStudentUpdateBinding
    private val nav by lazy { findNavController() }

    private val vm: StudentVM by activityViewModels()
    private val programVM: ProgramVM by activityViewModels()

    private val studentId by lazy { arguments?.getInt("studentId") ?: 0 }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStudentUpdateBinding.inflate(inflater, container, false)

        // -----------------------------------------------------------------------------------------

        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }

        // -----------------------------------------------------------------------------------------

        // TODO(13): Convert to extension function
        val adapter = getArrayAdapter<Program>()
        binding.spnProgram.adapter = adapter

        // TODO(14): Read all programs --> add to spinner
        lifecycleScope.launch{
            adapter.addAll(programVM.getAll())
            reset()
        }

        // -----------------------------------------------------------------------------------------

        return binding.root
    }

    private fun setGender(gender: String) {
        // TODO(16): Load gender radio buttons
        when (gender){
            "F" -> binding.radFemale.isChecked = true
            "M" -> binding.radMale.isChecked = true
        }
    }

    private fun setProgram(programId: String) {
        // TODO(17): Load program spinner
        for (i in 0..< binding.spnProgram.count) {
            val item = binding.spnProgram.getItemAtPosition(i) as Program
            if (item.id == programId){
                binding.spnProgram.setSelection(i)
                break
            }
        }

    }

    private fun reset() {
        // TODO(15): Read 1 student --> load data
        lifecycleScope.launch {
            val s = vm.get(studentId)
            if (s==null){
                nav.navigateUp()
                return@launch
            }

            binding.txtId.text = s.id.toString()
            binding.edtName.setText(s.name)
            setGender(s.gender)
//            setProgram(s.programId)
            binding.spnProgram.setSelection<Program> { it.id == s.programId }
            binding.edtName.requestFocus()
        }


    }

    private fun submit() {
        val s = Student(
            id        = binding.txtId.text.toString().toIntOrNull() ?: 0,
            name      = binding.edtName.text.toString().trim(),
            gender    = if (binding.radFemale.isChecked) "F" else "M",
            programId = (binding.spnProgram.selectedItem as Program).id
        )

        val e = vm.validate(s)
        if (e != "") {
            errorDialog(e)
            return
        }

        vm.update(s)
        nav.navigateUp()
    }

}








