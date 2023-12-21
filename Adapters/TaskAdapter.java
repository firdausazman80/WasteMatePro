package com.example.wastematepro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wastematepro.Models.TasksModel;
import com.example.wastematepro.R;
import com.example.wastematepro.databinding.RvTasksDesignBinding;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.viewHolder> {

    Context context;

    ArrayList<TasksModel>list;

    public TaskAdapter(Context context, ArrayList<TasksModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_tasks_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        TasksModel model = list.get(position);

        holder.binding.tasksName.setText(model.getName());
        holder.binding.tasksImage.setImageResource(model.getImage());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        RvTasksDesignBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = RvTasksDesignBinding.bind(itemView);
        }
    }
}
