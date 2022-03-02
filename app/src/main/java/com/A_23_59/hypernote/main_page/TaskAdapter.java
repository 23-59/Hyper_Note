package com.A_23_59.hypernote.main_page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.A_23_59.hypernote.R;
import com.A_23_59.hypernote.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    public List<Task> taskList;

    SendMoreDataToMainActivity moreDataToMainActivity;

    TaskAdapter(List<Task> tasks, SendMoreDataToMainActivity moreDataToMainActivity){

        taskList=new ArrayList<>(tasks);

        this.moreDataToMainActivity=moreDataToMainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindTask(taskList.get(position));
    }

    @Override
    public int getItemCount() {

        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkStatus;

        private ImageButton more;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            checkStatus=itemView.findViewById(R.id.task_info);

            more=itemView.findViewById(R.id.image_button_more);
        }

        public void bindTask(Task task){

            checkStatus.setOnCheckedChangeListener(null);

            checkStatus.setChecked(task.isCompleted());

            checkStatus.setText(task.getTaskTitle());

            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    moreDataToMainActivity.onMoreClicked(task);
                }
            });

           checkStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                   if (compoundButton.isChecked()) {

                       task.setCompleted(true);

                   }
                   else {

                       task.setCompleted(false);

                   }
                   moreDataToMainActivity.onChecked(task);
               }
           });
        }
    }

    public void addTask_Adapter(Task task){

        taskList.add(0,task);

        notifyItemInserted(0);
    }

    public void deleteTask(Task task){

        int deleteIndex= taskList.indexOf(task);

        taskList.remove(deleteIndex);

        notifyItemRemoved(deleteIndex);

    }

    public void editTask(Task task){

       int updateIndex= taskList.indexOf(task);

       taskList.set(updateIndex,task);

       notifyItemChanged(updateIndex);

    }

    public void searchTask(List<Task>searchedTask){

        taskList=searchedTask;

        notifyDataSetChanged();
    }



    public void deleteAll(){

        taskList.clear();

        notifyDataSetChanged();
    }

    public interface SendMoreDataToMainActivity {

        void onMoreClicked(Task clickedTask);

        void onChecked(Task task);
    }
}
