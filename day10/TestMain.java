package mocks.day10;

import mocks.day10.model.User;
import mocks.day10.model.store.EventManagerStore;

import java.time.LocalDate;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {

        EventManagerStore eventManagerStore = EventManagerStore.getInstance();
        Integer userId = eventManagerStore.registerUser("user1");
        User user = eventManagerStore.findUser(userId);
        user.setEmail("user1@example.com");

        eventManagerStore.updateUserDetails(user);

        System.out.println(eventManagerStore.findUser(userId));

        Integer eventId = eventManagerStore.createEvent(userId, 10, 11);

        System.out.println(eventManagerStore.getEvent(eventId));
        System.out.println(eventManagerStore.findUser(userId));


        eventManagerStore.updateEvent(userId, eventId,  12);
        System.out.println(eventManagerStore.getEvent(eventId));
        System.out.println(eventManagerStore.findUser(userId));


        Integer userId2 = eventManagerStore.registerUser("user2");

        eventManagerStore.inviteUserToEvent(eventId, List.of(userId2));
        System.out.println(eventManagerStore.getEvent(eventId));
        System.out.println(eventManagerStore.findUser(userId2));



    }
}
