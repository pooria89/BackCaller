package com.example.pooria.blackcaller.get_contactlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pooria.blackcaller.R;
import com.example.pooria.blackcaller.pojo.ReadContactRecycler;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterReadContactRecycler extends RecyclerView.Adapter<CustomAdapterReadContactRecycler.ViewHolder> {
    private List<ReadContactRecycler> list;
    private Context context;

    public CustomAdapterReadContactRecycler(List<ReadContactRecycler> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public CustomAdapterReadContactRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_show_contacts_in_recycler, parent, false);
        return new CustomAdapterReadContactRecycler.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomAdapterReadContactRecycler.ViewHolder holder, final int i) {
        holder.name.setText(list.get(i).getName());
        holder.phone.setText(list.get(i).getPhone());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "salam"+list.get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView phone;

        ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_name);
            phone = itemView.findViewById(R.id.txt_phone);
        }
    }
    public void filterList(ArrayList<ReadContactRecycler> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}
