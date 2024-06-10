package com.example.gopark.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gopark.R;
import com.example.gopark.data.Slot;

import java.util.List;

public class ReservedSlotsAdapter extends RecyclerView.Adapter<ReservedSlotsAdapter.ViewHolder> {
    private Context context;
    private List<Slot> reservedSlotsList;

    public ReservedSlotsAdapter(Context context, List<Slot> reservedSlotsList) {
        this.context = context;
        this.reservedSlotsList = reservedSlotsList;
    }

    @NonNull
    @Override
    public ReservedSlotsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reserved_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservedSlotsAdapter.ViewHolder holder, int position) {
        Slot slot = reservedSlotsList.get(position);
        holder.slotIdTextView.setText(slot.getSlotId());
        holder.entryTimeTextView.setText(String.valueOf(slot.getEntryTime()));
    }

    @Override
    public int getItemCount() {
        return reservedSlotsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView slotIdTextView, entryTimeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            slotIdTextView = itemView.findViewById(R.id.reservedslot);
            entryTimeTextView = itemView.findViewById(R.id.entrytime);
        }
    }
}

