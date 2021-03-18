package com.cos.javagg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.javagg.R;

import java.util.ArrayList;

public class RankedAdapter extends RecyclerView.Adapter<RankedAdapter.ViewHolder> {

    private ArrayList<String> mData = null;


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mrank;
        TextView mname;
        TextView mtier;
        TextView mlp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mrank = itemView.findViewById(R.id.rank);
            mname = itemView.findViewById(R.id.name);
            mtier = itemView.findViewById(R.id.tier);
            mlp = itemView.findViewById(R.id.lp);

        }
    }

    public RankedAdapter(ArrayList<String> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.ranked_item, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = mData.get(position) ;
        holder.mrank.setText(text) ;
        holder.mname.setText(text);
        holder.mtier.setText(text);
        holder.mlp.setText(text);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
