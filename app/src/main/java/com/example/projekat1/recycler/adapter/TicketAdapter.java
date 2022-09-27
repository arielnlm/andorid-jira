package com.example.projekat1.recycler.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projekat1.activities.LoginActivity;
import com.example.projekat1.R;
import com.example.projekat1.models.Stage;
import com.example.projekat1.models.Ticket;
import com.example.projekat1.viewmodels.RecyclerViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TicketAdapter extends ListAdapter<Ticket, TicketAdapter.ViewHolder> {

    private final Consumer<Ticket> onTicketClicked;

    public TicketAdapter(@NotNull DiffUtil.ItemCallback<Ticket> diffCallback, Consumer<Ticket> onTicketClicked){
        super(diffCallback);
        this.onTicketClicked = onTicketClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item, parent, false);
        return new ViewHolder(view, parent.getContext(), position -> {
            Ticket ticket = getItem(position);
            onTicketClicked.accept(ticket);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket ticket = getItem(position);
        holder.bind(ticket);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;

        private RecyclerViewModel recyclerViewModel;

        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);
            this.context = context;

            recyclerViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(RecyclerViewModel.class);

            itemView.setOnClickListener(v -> {
                if(getBindingAdapterPosition() != RecyclerView.NO_POSITION){
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }

        public void bind(Ticket ticket){
            ImageView imageView = itemView.findViewById(R.id.ticketPictureIv);
            Glide
                    .with(context)
                    .load("")
                    .error(ticket.getVector_image())
                    .into(imageView);
            ((TextView)itemView.findViewById(R.id.titleTv)).setText(ticket.getTitle());
            ((TextView)itemView.findViewById(R.id.descriptionTv)).setText(ticket.getDescription());
            Button upperBttn = itemView.findViewById(R.id.next_button);
            Button lowerBttn = itemView.findViewById(R.id.remove_button);

            if(ticket.getStage() == Stage.INPROGRESS){
                upperBttn.setVisibility(View.VISIBLE);
                lowerBttn.setVisibility(View.VISIBLE);
                lowerBttn.setText("<");
                upperBttn.setOnClickListener(e->{
                   // ticket.setStage(Stage.DONE);
                    recyclerViewModel.changeStage(ticket.getId(), Stage.DONE);
                });
                lowerBttn.setOnClickListener(e->{
                    recyclerViewModel.changeStage(ticket.getId(), Stage.TODO);

                });
            }
            else if(ticket.getStage() == Stage.DONE){
                upperBttn.setVisibility(View.GONE);
                lowerBttn.setVisibility(View.GONE);
            }
            else{
                upperBttn.setOnClickListener(e->{
                    recyclerViewModel.changeStage(ticket.getId(), Stage.INPROGRESS);
                });

                upperBttn.setVisibility(View.VISIBLE);

                if(LoginActivity.isAdminLogged){
                    lowerBttn.setVisibility(View.VISIBLE);

                    lowerBttn.setOnClickListener(e->{
                        recyclerViewModel.removeTicket(ticket.getId());
                    });
                }
                else{
                    lowerBttn.setVisibility(View.GONE);
                }
            }
        }
    }
}
