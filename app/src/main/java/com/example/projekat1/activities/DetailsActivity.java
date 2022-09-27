package com.example.projekat1.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projekat1.R;
import com.example.projekat1.models.Stage;
import com.example.projekat1.models.Ticket;

public class DetailsActivity extends AppCompatActivity {

    public static final String TICKET_KEY = "TICKET_KEY_SENDING_TICKET";
    private Ticket ticket;

    private TextView titleTv;
    private TextView typeTv;
    private TextView priorityTv;
    private Button estBttn;
    private Button loggedTimeBtn;
    private TextView descTv;
    private Button editBttn;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent i = getIntent();
        if(i == null) return;
        ticket = (Ticket) i.getSerializableExtra(TICKET_KEY);
        init();
    }

    private void init(){
        initView();
        initDefaultValues();
        initListeners();
    }

    private void initListeners(){
        loggedTimeBtn.setOnClickListener(e -> {
            int num = Integer.parseInt(loggedTimeBtn.getText().toString());
            num++;
            loggedTimeBtn.setText(num + "");
        });
        loggedTimeBtn.setOnLongClickListener(view -> {
                int num = Integer.parseInt(loggedTimeBtn.getText().toString());
                num--;
                if(num < 0) num = 0;
                loggedTimeBtn.setText(num + "");
                return true;
            });
        editBttn.setOnClickListener(e ->{
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra(EditActivity.SENT_TICKET_KEY, ticket);
            editActivityResultLauncher.launch(intent);
        });
    }

    ActivityResultLauncher<Intent> editActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    ticket = (Ticket) data.getSerializableExtra(EditActivity.SENT_TICKET_KEY);
                    if(ticket != null){
                        //new ViewModelProvider(this).get(RecyclerViewModel.class).updateTicket(returnTicket);
                        System.out.println("DOBIO SAM TICKEEEEET");
                        MainActivity.recyclerViewModel.updateTicket(ticket);
                        initDefaultValues();
                    }
                }
            }
    );

    private void initDefaultValues(){
        titleTv.setText(ticket.getTitle());
        typeTv.setText(ticket.getType().toString());
        priorityTv.setText(ticket.getPriority().toString());
        estBttn.setText(ticket.getEst() + "");
        loggedTimeBtn.setText("0");
        descTv.setText(ticket.getDescription());
    }

    private void initView(){
        this.editBttn = findViewById(R.id.editDetails);
        if(ticket.getStage() == Stage.DONE || LoginActivity.isUserLogged)
            editBttn.setVisibility(View.GONE);
        this.titleTv = findViewById(R.id.ticketTitleDetails);
        this.typeTv = findViewById(R.id.ticketTypeDetails);
        this.priorityTv = findViewById(R.id.ticketPriorityDetail);
        this.estBttn = findViewById(R.id.estButton);
        this.loggedTimeBtn = findViewById(R.id.loggedTimeButton);
        this.descTv = findViewById(R.id.ticketDescDetails);
        this.imageView = findViewById(R.id.ticketPictureDetailsIv);
        Glide
                .with(this)
                .load("")
                .error(ticket.getVector_image())
                .into(imageView);
    }
}