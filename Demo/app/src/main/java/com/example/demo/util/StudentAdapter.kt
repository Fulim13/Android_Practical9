package com.example.demo.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.data.Student
import com.example.demo.databinding.ItemStudentBinding

class StudentAdapter (
    val fn: (ViewHolder, Student) -> Unit = { _, _ -> }
) : ListAdapter<Student, StudentAdapter.ViewHolder>(Diff) {

    companion object Diff : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(a: Student, b: Student) = a.id == b.id
        override fun areContentsTheSame(a: Student, b: Student) = a == b
    }

    class ViewHolder(val binding: ItemStudentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = getItem(position)

        holder.binding.txtId.text = student.id.toString()
        holder.binding.txtName.text = student.name
        holder.binding.txtGender.text = student.gender
        holder.binding.txtProgramId.text = student.programId

        fn(holder, student)
    }

}