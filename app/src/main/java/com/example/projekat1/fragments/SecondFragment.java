package com.example.projekat1.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projekat1.R;
import com.example.projekat1.models.Priority;
import com.example.projekat1.models.Type;
import com.example.projekat1.viewmodels.RecyclerViewModel;

import timber.log.Timber;

public class SecondFragment extends Fragment {

    private RecyclerViewModel recyclerViewModel;
    private Spinner ticketType;
    private Spinner ticketPriority;
    private EditText estET;
    private EditText titleET;
    private EditText descET;
    private Button newBttn;
    private Spinner spinnerPriority;
    private Spinner spinnerType;

    private View view;


    public SecondFragment() {
        super(R.layout.fragment_second);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.e("ON CREATE");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.e("ON CREATE VIEW");
        view = super.onCreateView(inflater, container, savedInstanceState);
        init();
        return view;
    }

    private void init() {
        initView();
        initListeners();
        initSpinners();
    }

    private void initView(){
        recyclerViewModel = new ViewModelProvider(getActivity()).get(RecyclerViewModel.class);
        ticketType = view.findViewById(R.id.dropdown_ticket_type);
        ticketPriority = view.findViewById(R.id.dropdown_priority);
        estET = view.findViewById(R.id.ticket_est);
        titleET = view.findViewById(R.id.ticket_title);
        descET = view.findViewById(R.id.ticket_desc);
        newBttn = view.findViewById(R.id.add_ticket);
    }

    private void initListeners(){
        newBttn.setOnClickListener(e -> {
            Type t = Type.valueOf(ticketType.getSelectedItem().toString().toUpperCase());
            Priority p = Priority.valueOf(ticketPriority.getSelectedItem().toString().toUpperCase());
            String title = titleET.getText().toString();
            String desc = descET.getText().toString();

            if(checkData(t, p, estET.getText().toString(), title, desc)){
                System.out.println("####################### PRIKAZUJEEEEEEEEEEEEEEEEEEEEEEEEEEEEEm");
                recyclerViewModel.addTicket(t, p, Integer.parseInt(estET.getText().toString()), title, desc);
            }
                    else{
                System.out.println("NE PRIKAZUJEEEEEEEEEEEEEEEEEEEEEEEEEEEEEm");
            }
        });
    }

    private boolean checkData(Type t, Priority p, String est, String title, String desc){

        if(!title.isEmpty() && !desc.isEmpty() && !est.isEmpty())
            return true;
        showErrorMessage("Please check out fields again");
        return false;
    }

    private void showErrorMessage(String message) {
        Toast toast = Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);

        toast.show();
    }

    private void initSpinners(){
        spinnerPriority = view.findViewById(R.id.dropdown_priority);
        spinnerType = view.findViewById(R.id.dropdown_ticket_type);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.priorities, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(priorityAdapter);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        spinnerPriority.setPrompt("Priority");
        spinnerType.setPrompt("Type");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.e("ON VIEW CREATED");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Timber.e("ON DESTROY VIEW");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.e("ON DESTROY");
    }
}
