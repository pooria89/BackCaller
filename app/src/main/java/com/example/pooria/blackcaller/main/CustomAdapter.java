package com.example.pooria.blackcaller.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pooria.blackcaller.R;
import com.example.pooria.blackcaller.pojo.ReadLogContacts;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>  {
    private List<ReadLogContacts> list;
    private Context context;


    public CustomAdapter(Context context, List<ReadLogContacts> list) {
        this.context=context;
        this.list=list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_show_calls_list_layout_recycler_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        holder.viewnumber.setText(list.get(i).getNumber());
        holder.viewdate.setText(list.get(i).getDate());
        holder.viewduration.setText(list.get(i).getDuration());
        holder.viewnumber.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage(list.get(i).getDate());
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setTitle(list.get(i).getNumber());
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView viewnumber ;
        public TextView viewdate ;
        public TextView viewduration  ;

        ViewHolder(View itemView) {
            super(itemView);

            viewnumber = itemView.findViewById(R.id.textViewnumber);
            viewdate = itemView.findViewById(R.id.textViewdate);
            viewduration = itemView.findViewById(R.id.textViewduration);
        }

    }
    public void filterList(ArrayList<ReadLogContacts> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}

