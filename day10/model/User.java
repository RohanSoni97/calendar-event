package mocks.day10.model;

import mocks.day10.model.utils.IncrementUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private final Integer userId;
    private final String name;
    private  String email;

    private final Map<String, List<Slot>> slots;

    public User(String name) {
        if(name == null) {
            throw  new IllegalArgumentException("name is required");
        }
        this.userId = IncrementUtils.increment();
        this.name = name;
        this.slots = new HashMap<>();
    }


    public void setEmail(String email) {
        if(email == null) {
            throw  new IllegalArgumentException("email is required");
        }
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getUserId() {
        return userId;
    }

    public boolean slotAvailableOnDate(String date) {
        if(date == null) {
            throw  new IllegalArgumentException("date is required");
        }
        return  slots.containsKey(date) == false;
    }




    public boolean isSlotAvailable(String date, Integer startTime, Integer endTime) {
        if(date == null || startTime == null || endTime == null) {
            throw   new IllegalArgumentException("date or startTime is required");
        }


        if(slotAvailableOnDate(date)) {
            slots.put(date, new ArrayList<>());
        }

        for (Slot slot : slots.get(date)) {
            if (slot.getTime() > startTime && slot.getTime() < endTime  && slot.isOccupied()) {
                return false;
            }
        }
        return true;
    }

    public boolean bookSlot(String date, Integer startTime, Integer endTime, Integer eventId) {
        if(eventId == null) {
            throw   new IllegalArgumentException("eventId is required");
        }
        boolean slotAvailable = isSlotAvailable(date, startTime, endTime);

        if(slotAvailable) {
            List<Slot> slotList = new ArrayList<>();
            for (int time = startTime; time <= endTime; time++) {
                Slot slot = new Slot(date, time);
                slot.setSlotStatus(SlotStatus.BOOKED);
                slot.setEventId(eventId);
                slotList.add(slot);
            }
           this.slots.get(date).addAll(slotList);

        }
        return  slotAvailable;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", slots=" + slots +
                '}';
    }
}
