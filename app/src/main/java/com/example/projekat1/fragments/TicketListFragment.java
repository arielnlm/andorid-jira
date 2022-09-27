package com.example.projekat1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat1.activities.DetailsActivity;
import com.example.projekat1.R;
import com.example.projekat1.recycler.adapter.TicketAdapter;
import com.example.projekat1.recycler.differ.TicketDiffItemCallback;
import com.example.projekat1.viewmodels.RecyclerViewModel;

public class TicketListFragment extends Fragment {

    int tab = 0;
    public TicketListFragment(int tabNo) {
        super(R.layout.fragment_ticket_list);
        this.tab = tabNo;
    }
    private View view;
    private RecyclerViewModel recyclerViewModel;
    private RecyclerView recyclerView;
    private TicketAdapter ticketAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        init();
        return view;
    }

    private void init() {
        initView();
        initObservers();
        initRecycler();

    }

    private void initView() {
        recyclerViewModel = new ViewModelProvider(getActivity()).get(RecyclerViewModel.class);

        recyclerView = view.findViewById(R.id.fragment_ticket_list_RV);
    }

    private void initObservers(){
        recyclerViewModel.getTickets().observe(this.getViewLifecycleOwner(), tickets -> {
            ticketAdapter.submitList(tickets);
        });
    }

    private void initRecycler(){
        // on ticket clicked

        ticketAdapter = new TicketAdapter(new TicketDiffItemCallback(), ticket ->{
            //Toast.makeText(this.getContext(), " " + ticket.getId() + " ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getContext(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.TICKET_KEY, ticket);
            startActivity(intent);

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(ticketAdapter);

    }
}
