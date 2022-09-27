package com.example.projekat1.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.projekat1.models.Ticket;

public class TicketDiffItemCallback extends DiffUtil.ItemCallback<Ticket> {

    @Override
    public boolean areItemsTheSame(@NonNull Ticket oldItem, @NonNull Ticket newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Ticket oldItem, @NonNull Ticket newItem) {
        return oldItem.getDescription().equals(newItem.getDescription())
                && oldItem.getEst() == newItem.getEst()
                && oldItem.getPicture().equals(newItem.getPicture())
                && oldItem.getTitle().equals(newItem.getTitle())
                && oldItem.getPriority().equals(newItem.getPriority())
                && oldItem.getStage().equals(newItem.getStage())
                && oldItem.getType().equals(newItem.getType());
    }
}
