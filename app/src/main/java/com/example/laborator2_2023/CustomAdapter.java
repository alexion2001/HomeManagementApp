package com.example.laborator2_2023;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.TaskViewHolder> {

    private List<TaskModel> localDataSet;

    private static OnItemClickListener itemClickListener;

    public CustomAdapter(List<TaskModel> localDataSet, OnItemClickListener itemClickListener) {
        this.localDataSet = localDataSet;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {


        private final TextView taskText;
        private final TextView deadline;
        private final TextView username;

        private final CheckBox status;

        private final ConstraintLayout layout;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskText = itemView.findViewById(R.id.task_text);
            deadline = itemView.findViewById(R.id.task_deadline);
            username = itemView.findViewById(R.id.task_username);
            status = itemView.findViewById(R.id.task_done_checkbox);

            layout = itemView.findViewById(R.id.container);
        }
        public void bind(TaskModel item) {
            taskText.setText(item.getTaskText());
            deadline.setText(item.getDeadline());
            username.setText(item.getUsername());
            status.setChecked(item.getStatus());

         layout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 itemClickListener.onItemClick(item);
             }
         });

        }

    }
}
