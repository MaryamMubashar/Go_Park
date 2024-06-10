package com.example.gopark.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gopark.BookSlotActivity;
import com.example.gopark.R;
import com.example.gopark.data.Slot;

import java.util.List;

public class SlotsAdapter extends RecyclerView.Adapter<SlotsAdapter.SlotViewHolder> {
    private List<Slot> slots;
    private Context context;

    public SlotsAdapter(Context context, List<Slot> slots) {
        this.slots = slots;
        this.context = context;
    }

    @Override
    public SlotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_items, parent, false);
        return new SlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SlotViewHolder holder, int position) {
        Slot slot = slots.get(position);
        holder.slotIdText.setText("Zone: " + slot.getSlotId());
        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookSlotActivity.class);
                intent.putExtra("slot_id", slot.getSlotId());
                intent.putExtra("rent_per_hour", 250); // Assuming a fixed rate for example
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    public static class SlotViewHolder extends RecyclerView.ViewHolder {
        TextView slotIdText;
        Button bookButton;

        public SlotViewHolder(View itemView) {
            super(itemView);
            slotIdText = itemView.findViewById(R.id.availableslot);
            bookButton = itemView.findViewById(R.id.bookNowbtn);
        }
    }
}
