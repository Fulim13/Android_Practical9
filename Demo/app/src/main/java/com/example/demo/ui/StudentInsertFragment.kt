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
import com.example.demo.databinding.FragmentStudentInsertBinding
import com.example.demo.util.errorDialog
import com.example.demo.util.getArrayAdapter
import kotlinx.coroutines.launch

class StudentInsertFragment : Fragment() {

    private lateinit var binding: FragmentStudentInsertBinding
    private val nav by lazy { findNavController() }

    private val vm: StudentVM by activityViewModels()
    private val programVM: ProgramVM by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStudentInsertBinding.inflate(inflater, container, false)

        // -----------------------------------------------------------------------------------------

        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }

        // -----------------------------------------------------------------------------------------

        // TODO(11A): Convert to extension function
        val adapter = getArrayAdapter<Program>()
        binding.spnProgram.adapter = adapter

        // TODO(12): Read all programs --> add to spinner
        lifecycleScope.launch{
            adapter.addAll(programVM.getAll())
            reset()
        }



        // -----------------------------------------------------------------------------------------

        return binding.root
    }

    private fun reset() {
        binding.edtName.text.clear()
        binding.radFemale.isChecked = true
        binding.spnProgram.setSelection(0)

        binding.edtName.requestFocus()
    }

    private fun submit() {
        val s = Student(
            id        = 0, //when id is 0, it will auto increment
            name      = binding.edtName.text.toString().trim(),
            gender    = if (binding.radFemale.isChecked) "F" else "M",
            programId = (binding.spnProgram.selectedItem as Program).id
        )

        val e = vm.validate(s)
        if (e != "") {
            errorDialog(e)
            return
        }

        vm.insert(s)
        nav.navigateUp()
    }

}








