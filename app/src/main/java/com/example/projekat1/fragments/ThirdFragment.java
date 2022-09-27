package com.example.projekat1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.projekat1.activities.DetailsActivity;
import com.example.projekat1.R;
import com.example.projekat1.recycler.adapter.TicketAdapter;
import com.example.projekat1.recycler.differ.TicketDiffItemCallback;
import com.example.projekat1.viewmodels.RecyclerViewModel;
import com.example.projekat1.viewpager.TabPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class ThirdFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private EditText searchText;

    private RecyclerViewModel recyclerViewModel;
    private TicketAdapter ticketAdapter;

    private ViewPager viewPager;
    private TabLayout tabLayout;


    public ThirdFragment() {
        super(R.layout.fragment_third);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerViewModel = new ViewModelProvider(getActivity()).get(RecyclerViewModel.class);
        init();

        return view;
    }

    private void init(){
        initView();
        initListeners();
        //initObservers();
        //initRecycler();
        initTabs();
    }

    private void initTabs() {
        viewPager.setAdapter(new TabPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initView(){
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.tabViewPager);
        //recyclerView = view.findViewById(R.id.ticket_list_RV);
        searchText = view.findViewById(R.id.inputEt);
    }

    private void initListeners(){
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                recyclerViewModel.filterTickets(editable.toString());
            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if(pos == 0)
                    recyclerViewModel.filterTicketsTab("TODO");
                else if(pos == 1)
                    recyclerViewModel.filterTicketsTab("PROGRESS");
                else if(pos == 2)
                    recyclerViewModel.filterTicketsTab("DONE");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initObservers(){
        recyclerViewModel.getTickets().observe(this.getViewLifecycleOwner(), tickets -> {
            ticketAdapter.submitList(tickets);
        });
    }

    private void initRecycler(){
        // on ticket clicked
        ticketAdapter = new TicketAdapter(new TicketDiffItemCallback(), ticket ->{
            Toast.makeText(this.getContext(), " " + ticket.getId() + " ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getContext(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.TICKET_KEY, ticket);
            startActivity(intent);

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(ticketAdapter);

    }
}
