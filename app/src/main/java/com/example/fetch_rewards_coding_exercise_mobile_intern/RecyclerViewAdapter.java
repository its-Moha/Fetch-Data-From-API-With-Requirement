package com.example.fetch_rewards_coding_exercise_mobile_intern;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    //initialize variables

    List<MainData> dataArrayList;
    MainActivity mainActivity;
    //create constructor


    public RecyclerViewAdapter (List<MainData> dataArrayList, MainActivity mainActivity) {
        this.dataArrayList = dataArrayList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        //this method recycling the view holder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        //initialize Main data
        MainData data = dataArrayList.get(position);
        //set name on text view
        holder.listId.setText(String.format("list_id : %s", data.getListId()));
        holder.name.setText(String.format("name : %s", data.getName()));
        holder.id.setText(String.format("id : %s", data.getId()));
    }

    @Override
    public int getItemCount () {
        return dataArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //initialize variables


        TextView listId, name, id;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);

            //assign variables


            listId = itemView.findViewById(R.id.list_id);
            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);

        }

    }

}
