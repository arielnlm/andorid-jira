package com.example.projekat1.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projekat1.R;
import com.example.projekat1.viewmodels.RecyclerViewModel;

import timber.log.Timber;


public class FirstFragment extends Fragment {

    public FirstFragment() {
        super(R.layout.fragment_first);
    }

    private RecyclerViewModel recyclerViewModel;

    private TextView todoEnhancementTv;
    private TextView todoBugsTv;
    private TextView progressEnhancementTv;
    private TextView progressBugsTv;
    private TextView doneBugsTv;
    private TextView doneEnhancementTv;
    private TextView todoTv;
    private TextView progressTv;
    private TextView doneTv;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("PRAVIM VIEW OPEET$$$$$$$$$$$$$$$$$$$$$$$");
        init();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        loadValues();
    }

    private void init(){
        initView();
        loadValues();
    }

    private void loadValues() {
        int todoBugs = recyclerViewModel.getTodoBugsEst();
        int todoEnc = recyclerViewModel.getTodoEncEst();
        int progressEnc = recyclerViewModel.getProgressEncEst();
        int progressBugs = recyclerViewModel.getProgressBugsEst();
        int doneBugs = recyclerViewModel.getDoneBugsEst();
        int doneEnc = recyclerViewModel.getDoneEncEst();

        todoBugsTv.setText(todoBugs + "");
        todoEnhancementTv.setText(todoEnc + "");
        progressBugsTv.setText(progressBugs + "");
        progressEnhancementTv.setText(progressEnc + "");
        doneBugsTv.setText(doneBugs + "");
        doneEnhancementTv.setText(doneEnc + "");
        todoTv.setText((todoBugs + todoEnc)  + "");
        progressTv.setText((progressBugs + progressEnc)  + "");
        doneTv.setText((doneEnc + doneBugs)  + "");

    }

    private void initView(){
        recyclerViewModel = new ViewModelProvider(getActivity()).get(RecyclerViewModel.class);
        todoBugsTv = view.findViewById(R.id.text_todo_bugs_total);
        todoEnhancementTv = view.findViewById(R.id.text_todo_enchancements_total);
        progressBugsTv = view.findViewById(R.id.text_progress_bugs_total);
        progressEnhancementTv = view.findViewById(R.id.text_progress_enchancements_total);
        doneBugsTv = view.findViewById(R.id.text_done_bugs_total);
        doneEnhancementTv = view.findViewById(R.id.text_done_enchancements_total);
        todoTv = view.findViewById(R.id.text_todo_total);
        progressTv = view.findViewById(R.id.text_progress_total);
        doneTv = view.findViewById(R.id.text_done_total);
    }

}