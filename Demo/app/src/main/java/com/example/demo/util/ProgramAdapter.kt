package com.example.demo.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.data.Program
import com.example.demo.databinding.ItemProgramBinding

class ProgramAdapter (
    val fn: (ViewHolder, Program) -> Unit = { _, _ -> }
) : ListAdapter<Program, ProgramAdapter.ViewHolder>(Diff) {

    companion object Diff : DiffUtil.ItemCallback<Program>() {
        override fun areItemsTheSame(a: Program, b: Program) = a.id == b.id
        override fun areContentsTheSame(a: Program, b: Program) = a == b
    }

    class ViewHolder(val binding: ItemProgramBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemProgramBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val program = getItem(position)

        holder.binding.txtId.text = program.id
        holder.binding.txtName.text = program.name

        fn(holder, program)
    }

}