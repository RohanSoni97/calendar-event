package mocks.day10.model.store;

import mocks.day10.model.Event;
import mocks.day10.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventManagerStore {

    private final Map<Integer, Event> events = new HashMap<>();
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Map<String , Set<Integer>>> userIdToSlotIdMap = new HashMap<>();





    private EventManagerStore() {
    }
    private static EventManagerStore instance = new EventManagerStore();

    public  static EventManagerStore getInstance() {
        return  instance;
    }

    public synchronized Integer registerUser(String  userName) {
        if(userName == null) {
            throw new IllegalArgumentException("userName is null");
        }
        User user = new User(userName);
        users.put(user.getUserId(), user );

        return  user.getUserId();
    }

    public synchronized void updateUserDetails(User user) {
        if(user == null) {
            throw  new IllegalArgumentException("user is null");
        }

        User user_ = findUser(user.getUserId());
        user_.setEmail(user.getEmail());
    }


    public synchronized User findUser(Integer userId) {
        if(userId == null) {
            throw new IllegalArgumentException("userId is null");
        }
        User user = users.get(userId);
        if(user == null) {
            throw  new IllegalArgumentException("user doesn't exists ");
        }
        return  user;
    }


    public synchronized Integer createEvent(Integer userId, Integer startTime, Integer endTime) {
        if(startTime == null || endTime == null) {
            throw new IllegalArgumentException("startTime or endTime is null");
        }
        //  String today = LocalDate.now().toString();
        User user = findUser(userId);
        boolean slotAvailable = user.isSlotAvailable(LocalDate.now().toString(), startTime, endTime);
        if(!slotAvailable) {
            throw   new IllegalArgumentException("slot is not available");
        }
        Event event = new Event(userId, startTime, endTime);
        event.addUsersToEvent(List.of(userId));
        user.bookSlot(LocalDate.now().toString(), startTime, endTime, event.getEventId());

        this.events.put(event.getEventId(), event);

        if(!this.userIdToSlotIdMap.containsKey(userId)) {
            this.userIdToSlotIdMap.put(userId, new HashMap<>());
        }
        if(!this.userIdToSlotIdMap.get(userId).containsKey(LocalDate.now().toString())) {
            this.userIdToSlotIdMap.get(userId).put(LocalDate.now().toString(), new HashSet<>());
        }

        Set<Integer> slotIds = this.userIdToSlotIdMap.get(userId).get(LocalDate.now().toString());
        for (int time = startTime; time <= endTime; time++) {
            slotIds.add(time);
        }

        return event.getEventId();
    }


    public  synchronized Event getEvent(Integer eventId) {
        if(eventId == null) {
            throw new IllegalArgumentException("eventId is null");
        }

        Event event = this.events.get(eventId);

        if(event == null) {
            throw   new IllegalArgumentException("event doesn't exists ");
        }

        return event;
    }


    // TODO: only increase allowed
    public synchronized Integer updateEvent(Integer userId,Integer eventId ,Integer endTime) {
        if( endTime == null) {
            throw new IllegalArgumentException("startTime or endTime is null");
        }
        User user = findUser(userId);
        if(user == null) {
            throw   new IllegalArgumentException("user doesn't exists ");
        }
        Event event = getEvent(eventId);
        if(event == null) {
            throw   new IllegalArgumentException("event doesn't exists ");
        }

        Set<Integer> slotIds = userIdToSlotIdMap.get(userId).get(LocalDate.now().toString());



        user.bookSlot(LocalDate.now().toString(), event.getStartTime() +1, endTime, event.getEventId());

        for (int time = event.getEndTime() + 1; time <= endTime; time++) {
            slotIds.add(time);
        }
        event.setEndTime(endTime);

        return  event.getEventId();

    }


    public void inviteUserToEvent(Integer eventId, List<Integer> userIds) {
        if(userIds == null || eventId == null || userIds.isEmpty()) {
            throw new IllegalArgumentException("userIds or eventId is null");
        }

        Event event = getEvent(eventId);

        for (int userId : userIds) {
            users.get(userId).bookSlot(event.getDate(),  event.getStartTime(), event.getEndTime(), event.getEventId());
        }

        event.addUsersToEvent(userIds);
    }
}
