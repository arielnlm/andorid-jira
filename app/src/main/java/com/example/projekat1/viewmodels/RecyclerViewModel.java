package com.example.projekat1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projekat1.models.Priority;
import com.example.projekat1.models.Stage;
import com.example.projekat1.models.Ticket;
import com.example.projekat1.models.Type;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecyclerViewModel extends ViewModel {

    private static int counter = 101;
    private int todoBugsEst = 0, todoEncEst = 0;
    private int progressBugsEst = 0, progressEncEst = 0;
    private int doneBugsEst = 0, doneEncEst = 0;
    private int currentTab = 0;

    private final MutableLiveData<List<Ticket>> tickets = new MutableLiveData<>();
    private ArrayList<Ticket> ticketList = new ArrayList<>();
    private static final SecureRandom random = new SecureRandom();

    private List<Ticket> currentFilteredList;

    public RecyclerViewModel() {
        // default tickets
        for(int i=0; i<=100; i++){
            Type t = randomEnum(Type.class);
            Ticket ticket = new Ticket(i, t, randomEnum(Priority.class), random.nextInt(10) + 1, t + " " + i, "Desc " + i);
            ticket.setStage(randomEnum(Stage.class));
            ticketList.add(ticket);
            switch (ticket.getStage()){
                case DONE: if(t == Type.BUG) doneBugsEst += ticket.getEst(); else doneEncEst += ticket.getEst(); break;
                case INPROGRESS: if(t == Type.BUG) progressBugsEst += ticket.getEst(); else progressEncEst += ticket.getEst(); break;
                case TODO: if(t == Type.BUG) todoBugsEst += ticket.getEst(); else todoEncEst += ticket.getEst(); break;
            }
        }

        ArrayList<Ticket> listToSubmit = new ArrayList<>(ticketList);
        tickets.setValue(listToSubmit);
    }

    public LiveData<List<Ticket>> getTickets() {return tickets;}


    public void filterTickets(String filter){
        List<Ticket> filteredList; //ticketList.stream().filter(ticket -> ticket.getTitle().toLowerCase().startsWith(filter.toLowerCase())).collect(Collectors.toList());
        if(currentTab == 0){
            System.out.println("FILTRIRAJ PO TODO");
            filteredList = ticketList.stream().filter(ticket -> ticket.getTitle().toLowerCase().startsWith(filter.toLowerCase()) && ticket.getStage().equals(Stage.TODO)).collect(Collectors.toList());
        }
        else if(currentTab == 1){
            System.out.println("FILTRIRAJ PO RPOGREES");
            filteredList = ticketList.stream().filter(ticket -> ticket.getTitle().toLowerCase().startsWith(filter.toLowerCase()) && ticket.getStage().equals(Stage.INPROGRESS)).collect(Collectors.toList());
        }
        else{
            System.out.println("FILTRIRAJ PO DONBE");
            filteredList = ticketList.stream().filter(ticket -> ticket.getTitle().toLowerCase().startsWith(filter.toLowerCase()) && ticket.getStage().equals(Stage.DONE)).collect(Collectors.toList());
        }
        currentFilteredList = new ArrayList<>(filteredList);

        tickets.setValue(filteredList);
    }

    public void filterTicketsTab(String filter){
        List<Ticket> filteredList;

        if(filter.equalsIgnoreCase("TODO")){
            filteredList = ticketList.stream().filter(ticket -> ticket.getStage().equals(Stage.TODO)).collect(Collectors.toList());
            currentTab = 0;
        }
        else if(filter.equalsIgnoreCase("DONE")){
            filteredList = ticketList.stream().filter(ticket -> ticket.getStage().equals(Stage.DONE)).collect(Collectors.toList());
            currentTab = 2;
        }
        else{
            filteredList = ticketList.stream().filter(ticket -> ticket.getStage().equals(Stage.INPROGRESS)).collect(Collectors.toList());
            currentTab = 1;
        }
        currentFilteredList = new ArrayList<>(filteredList);
        tickets.setValue(filteredList);
    }

    public int addTicket(Type type, Priority priority, int est, String title, String description){
        int id = counter++;
        Ticket t = new Ticket(id, type, priority, est, title, description);
        ticketList.add(t);

        if(currentFilteredList != null && currentTab == 0){
            currentFilteredList.add(t);
            ArrayList<Ticket> listToSubmit = new ArrayList<>(currentFilteredList);
            tickets.setValue(listToSubmit);
        }

        switch (type){
            case BUG: todoBugsEst += est; break;
            case ENHANCEMENT: todoEncEst += est; break;
        }

        return id;
    }

    public void changeStage(int id, Stage to){
        Optional<Ticket> ticketObject = currentFilteredList.stream().filter(ticket -> ticket.getId() == id).findFirst();
        if(ticketObject.isPresent()){
            Stage old = ticketObject.get().getStage();
            ticketObject.get().setStage(to);
            currentFilteredList.remove(ticketObject.get());
            ArrayList<Ticket> listToSubmit = new ArrayList<>(currentFilteredList);
            tickets.setValue(listToSubmit);
            Ticket t = ticketObject.get();
            switch (to){
                case DONE: if(t.getType() == Type.BUG) doneBugsEst += t.getEst(); else doneEncEst += t.getEst(); break;
                case INPROGRESS: if(t.getType() == Type.BUG) progressBugsEst += t.getEst(); else progressEncEst += t.getEst(); break;
                case TODO: if(t.getType() == Type.BUG) todoBugsEst += t.getEst(); else todoEncEst += t.getEst(); break;
            }
            switch (old){
                case DONE: if(t.getType() == Type.BUG) doneBugsEst -= t.getEst(); else doneEncEst -= t.getEst(); break;
                case INPROGRESS: if(t.getType() == Type.BUG) progressBugsEst -= t.getEst(); else progressEncEst -= t.getEst(); break;
                case TODO: if(t.getType() == Type.BUG) todoBugsEst -= t.getEst(); else todoEncEst -= t.getEst(); break;
            }
        }
    }

    public void removeTicket(int id){
        Optional<Ticket> ticketObject = ticketList.stream().filter(ticket -> ticket.getId() == id).findFirst();
        if(ticketObject.isPresent()){
            ticketList.remove(ticketObject.get());
            currentFilteredList.remove(ticketObject.get());
            ArrayList<Ticket> listToSubmit = new ArrayList<>(currentFilteredList);
            tickets.setValue(listToSubmit);
            Ticket t = ticketObject.get();
            switch (t.getStage()){
                case DONE: if(t.getType() == Type.BUG) doneBugsEst -= t.getEst(); else doneEncEst -= t.getEst(); break;
                case INPROGRESS: if(t.getType() == Type.BUG) progressBugsEst -= t.getEst(); else progressEncEst -= t.getEst(); break;
                case TODO: if(t.getType() == Type.BUG) todoBugsEst -= t.getEst(); else todoEncEst -= t.getEst(); break;
            }
        }
    }

    public void updateTicket(Ticket t){
        Optional<Ticket> ticketObject = ticketList.stream().filter((ticket -> t.getId() == ticket.getId())).findFirst();
        if(ticketObject.isPresent()){
            Ticket old = ticketObject.get();
            ticketList.set(ticketList.lastIndexOf(old), t);
            currentFilteredList.set(currentFilteredList.lastIndexOf(old), t);
            switch (t.getStage()){
                case DONE: if(t.getType() == Type.BUG) doneBugsEst += t.getEst(); else doneEncEst += t.getEst(); break;
                case INPROGRESS: if(t.getType() == Type.BUG) progressBugsEst += t.getEst(); else progressEncEst += t.getEst(); break;
                case TODO: if(t.getType() == Type.BUG) todoBugsEst += t.getEst(); else todoEncEst += t.getEst(); break;
            }
            switch (old.getStage()){
                case DONE: if(old.getType() == Type.BUG) doneBugsEst -= old.getEst(); else doneEncEst -= old.getEst(); break;
                case INPROGRESS: if(old.getType() == Type.BUG) progressBugsEst -= old.getEst(); else progressEncEst -= old.getEst(); break;
                case TODO: if(old.getType() == Type.BUG) todoBugsEst -= old.getEst(); else todoEncEst -= old.getEst(); break;
            }
        }
        ArrayList<Ticket> listToSubmit = new ArrayList<>(currentFilteredList);
        tickets.setValue(listToSubmit);
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public int getTodoBugsEst() {
        return todoBugsEst;
    }

    public int getTodoEncEst() {
        return todoEncEst;
    }

    public int getProgressBugsEst() {
        return progressBugsEst;
    }

    public int getProgressEncEst() {
        return progressEncEst;
    }

    public int getDoneBugsEst() {
        return doneBugsEst;
    }

    public int getDoneEncEst() {
        return doneEncEst;
    }
}
