package com.example.projekat1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.projekat1.R;
import com.example.projekat1.models.Priority;
import com.example.projekat1.models.Ticket;
import com.example.projekat1.models.Type;
import com.example.projekat1.viewmodels.RecyclerViewModel;

import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    public static final String SENT_TICKET_KEY = "KJNKAJWNDKJANWKJN@";

    private Ticket ticket;

    private Spinner ticketType;
    private Spinner ticketPriority;
    private EditText estET;
    private EditText titleET;
    private EditText descET;
    private Button saveBttn;
    private TextView editTitleTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);
        Intent i = getIntent();
        if(i == null) return;
        ticket = (Ticket) i.getSerializableExtra(SENT_TICKET_KEY);
        init();
    }

    private void init(){
        initView();
        initSpinners();
        initValues();
        initListeners();
    }

    private void initListeners() {
        saveBttn.setOnClickListener(e -> {
            Intent intent = new Intent();
            updateTicketFields();
            intent.putExtra(SENT_TICKET_KEY, ticket);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void updateTicketFields() {
        ticket.setType(Type.valueOf(ticketType.getSelectedItem().toString().toUpperCase()));
        ticket.setPriority(Priority.valueOf(ticketPriority.getSelectedItem().toString().toUpperCase()));
        ticket.setEst(Integer.parseInt(estET.getText().toString()));
        ticket.setTitle(titleET.getText().toString());
        ticket.setDescription(descET.getText().toString());
    }

    private void initSpinners(){
        ticketPriority = findViewById(R.id.dropdown_priority);
        ticketType = findViewById(R.id.dropdown_ticket_type);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this, R.array.priorities, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ticketPriority.setAdapter(priorityAdapter);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ticketType.setAdapter(typeAdapter);

    }

    private void initValues() {
        ticketType.setSelection(ticket.getType().ordinal());
        ticketPriority.setSelection(ticket.getPriority().ordinal());
        estET.setText(ticket.getEst() + "");
        titleET.setText(ticket.getTitle());
        descET.setText(ticket.getDescription());
        saveBttn.setText("Save");
        editTitleTv.setText("Edit ticket");
    }

    private void initView(){
        ticketType = findViewById(R.id.dropdown_ticket_type);
        ticketPriority = findViewById(R.id.dropdown_priority);
        estET = findViewById(R.id.ticket_est);
        titleET = findViewById(R.id.ticket_title);
        descET = findViewById(R.id.ticket_desc);
        saveBttn = findViewById(R.id.add_ticket);
        editTitleTv = findViewById(R.id.ticket_text);

    }
}
