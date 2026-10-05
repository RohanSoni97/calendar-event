package mocks.day10.model;

import java.util.Date;

public class Slot {
    private SlotStatus slotStatus;
    private final String date;
    private final Integer time;
    private Integer eventId;

    public Slot(String date, Integer time) {
        if(date == null || date.isEmpty() || time == null) throw new IllegalArgumentException("date or timne can't be null ");
        this.date = date;
        this.time = time;
    }

    public SlotStatus getSlotStatus() {
        return slotStatus;
    }

    public String getDate() {
        return date;
    }

    public Integer getTime() {
        return time;
    }

    public void setSlotStatus(SlotStatus slotStatus) {
        if(slotStatus == null) throw new IllegalArgumentException("slotStatus can't be null");
        this.slotStatus = slotStatus;
    }
    public boolean isOccupied() {
        return slotStatus == SlotStatus.BOOKED;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getEventId() {
        return eventId;
    }
}
