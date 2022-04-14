package com.A_23_59.hypernote.main_page;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.A_23_59.hypernote.R;
import com.A_23_59.hypernote.model.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    public List<Task> taskList;

    boolean selectedStatus;

    boolean checkAllStatus;

    char selectAllStatus='A';

    boolean resetStatus;

    private List<Task> selectedTasks=new ArrayList<>();

    public List<Task> getSelectedTasks() {
        return selectedTasks;
    }

    private SelectedItems selectedItems;

    SendMoreDataToMainActivity moreDataToMainActivity;

    private static final String TAG = "TaskAdapter";

    TaskAdapter(List<Task> tasks, SendMoreDataToMainActivity moreDataToMainActivity, SelectedItems selectedItems){

        taskList=new ArrayList<>(tasks);

        this.moreDataToMainActivity=moreDataToMainActivity;

        this.selectedItems = selectedItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i(TAG, "onCreateViewHolder ");

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout,parent,false));

    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindTask(taskList.get(position));

          if (selectedStatus && taskList.get(position).isSelectedMode())
             holder.selectButton.setImageResource(R.drawable.ic_round_check_circle_24);

       else
           holder.selectButton.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);

        Log.i(TAG, "onBindViewHolder ");
    }

    @Override
    public int getItemCount() {

        return taskList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox taskCheckStatus;

        private final ImageButton more;

        private final ImageButton selectButton;

        short count=0;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            Log.i(TAG, "ViewHolder");

            taskCheckStatus =itemView.findViewById(R.id.task_info);

            more=itemView.findViewById(R.id.image_button_more);

            selectButton=itemView.findViewById(R.id.btn_select_check);
        }


        public void bindTask(Task task){

            Log.i(TAG, "bindTask: reached");

            taskCheckStatus.setOnCheckedChangeListener(null);

            selectButton.setOnClickListener(null);

            taskCheckStatus.setChecked(task.isCompleted());

            taskCheckStatus.setText(task.getTaskTitle());

            more.setOnClickListener(view -> moreDataToMainActivity.onMoreClicked(task));

            if (selectedStatus) {

                more.setEnabled(false);

                more.setVisibility(View.INVISIBLE);

                taskCheckStatus.setEnabled(false);

                selectButton.setVisibility(View.VISIBLE);

                selectButton.setEnabled(true);
            }

            else {

                taskCheckStatus.setEnabled(true);

                more.setEnabled(true);

                more.setVisibility(View.VISIBLE);

                selectButton.setEnabled(false);

                selectButton.setVisibility(View.INVISIBLE);


            }

            if (resetStatus)
                taskCheckStatus.setChecked(false);


            if (checkAllStatus)
                taskCheckStatus.setChecked(true);


             if (selectAllStatus == 'B') {

                if (selectedTasks.isEmpty()){

                    selectedTasks.addAll(taskList);
                }

                for (Task task1:selectedTasks) {

                    task1.setSelectedMode(true);

                    selectedItems.onItemAddedToAdapterSelection(task1);

                    count=1;
                }

                selectedItems.onItemAddedToAdapterSelection(String.valueOf(selectedTasks.size()));

                selectButton.setImageResource(R.drawable.ic_round_check_circle_24);


            }

            else if (selectAllStatus=='C'|| !selectedStatus){

                selectButton.setOnClickListener(null);

                selectedTasks.removeAll(taskList);

                for (Task task1:taskList) {

                    task1.setSelectedMode(false);

                    selectedItems.onItemAddedToAdapterSelection(task1);

                    count=0;

                }




                    selectedItems.onItemAddedToAdapterSelection(String.valueOf(selectedTasks.size()));

                    selectButton.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);


            }

           taskCheckStatus.setOnCheckedChangeListener((compoundButton, b) -> {

               task.setCompleted(compoundButton.isChecked());

               moreDataToMainActivity.updateCheckInDataBase(task);
           });

                selectButton.setOnClickListener(view -> {

                    count++;

                    switch (count){


                        case 1:

                            selectButton.setImageResource(R.drawable.ic_round_check_circle_24);

                            task.setSelectedMode(true);

                            selectedTasks.add(task);

                            String size1=String.valueOf(selectedTasks.size());

                            selectedItems.onItemAddedToAdapterSelection(size1,task);

                            break;

                        case 2:

                            selectButton.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);

                            task.setSelectedMode(false);

                            selectedTasks.remove(task);

                            String size2=String.valueOf(selectedTasks.size());

                            selectedItems.onItemAddedToAdapterSelection(size2,task);

                            count=0;

                            break;

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

    public void selectAll(){

        selectAllStatus='B';

        notifyDataSetChanged();
    }

    public void deselectAll(){

        selectAllStatus ='C';

        notifyDataSetChanged();
    }

    public void resetAll(){

       resetStatus=true;

       notifyDataSetChanged();

    }

    public void checkAll(){

        checkAllStatus=true;

        notifyDataSetChanged();

    }


    /**
     * this method will turn on selection mode so select button visibility 
     * will be true,
     * when an task is selected it will be added to 
     * selected list ,and task checkbox will be disabled in order to prevent accidental check change
     */
    public void startSelection(){

        selectedStatus=true;

        notifyDataSetChanged();
    }

    public void exitSelection(){

        selectedStatus=false;

        selectAllStatus='A';

        selectedTasks.clear();

        resetStatus=false;

        selectedItems.onItemAddedToAdapterSelection(String.valueOf(selectedTasks.size()));

        notifyDataSetChanged();
    }

    public void deleteSelectedItems(){

        taskList.removeAll(selectedTasks);

        selectedTasks.clear();

        selectedItems.onSelectionIsFinished();

        notifyDataSetChanged();
    }

    public interface SendMoreDataToMainActivity {

        void onMoreClicked(Task clickedTask);

        void updateCheckInDataBase(Task task);
    }

    public interface SelectedItems {

        void onItemAddedToAdapterSelection(String itemsSize);

        void onItemAddedToAdapterSelection( Task task);

        void onItemAddedToAdapterSelection(String itemsSize,Task task);

        void onSelectionIsFinished();



    }

}
