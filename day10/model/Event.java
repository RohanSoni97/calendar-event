package mocks.day10.model;

import mocks.day10.model.utils.IncrementUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {
    private final Integer eventId;
    private String name;
    private final Integer createdBy;
    private final List<Integer> userIds;
    private final Map<Integer, Response> eventResponses;

    private String date;
    private Integer startTime;
    private Integer endTime;


    public Event(Integer createdBy, Integer startTime, Integer endTime) {
        if(createdBy == null || startTime == null || endTime == null) {
            throw  new IllegalArgumentException("createdBy cannot be null");
        }
        this.eventId = IncrementUtils.increment();
       this.createdBy = createdBy;
       this.startTime = startTime;
       this.endTime = endTime;
       this.date = LocalDate.now().toString();
       this.userIds = new ArrayList<>();
       this.eventResponses = new HashMap<>();
    }

    public Integer getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public List<Integer> getUserIds() {
        return List.copyOf(userIds);
    }

    public Map<Integer, Response> getEventResponses() {
        return Collections.unmodifiableMap(eventResponses);
    }

    public String getDate() {
        return date;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void addUsersToEvent(List<Integer> userIds) {
        if(userIds == null || userIds.isEmpty()) {
            throw  new IllegalArgumentException("userIds cannot be null or empty");
        }


        for (Integer userId : this.userIds) {

            if(!eventResponses.containsKey(userId)) {
                eventResponses.put(userId, Response.NO_RESPONSE);
            }
        }

        this.userIds.addAll(userIds);
    }

    public String acceptEvent(Integer userId) {
        if(userId == null) {
            throw  new IllegalArgumentException("userId cannot be null");
        }
        Response response = eventResponses.get(userId);

        if(response == null) {
            throw   new IllegalArgumentException("userId not part of event !!");
        }
        eventResponses.put(userId, Response.ACCEPTED);
        return "User " + userId + " accepted event 'Meeting' ";
    }


    public String rejectEvent(Integer userId) {
        if(userId == null) {
            throw  new IllegalArgumentException("userId cannot be null");
        }
        Response response = eventResponses.get(userId);

        if(response == null) {
            throw   new IllegalArgumentException("userId not part of event !!");
        }
        eventResponses.put(userId, Response.REJECTED);
        return "User " + userId + " REJECTED event 'Meeting' ";
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", name='" + name + '\'' +
                ", createdBy=" + createdBy +
                ", userIds=" + userIds +
                ", eventResponses=" + eventResponses +
                ", date='" + date + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public void setEndTime(Integer endTime) {
        if(endTime == null) {
            throw   new IllegalArgumentException("endTime cannot be null");
        }
        this.endTime = endTime;
    }
}
