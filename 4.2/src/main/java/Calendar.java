import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Calendar {

    private final HashMap<String, Event> events;

    public Calendar(HashMap<String, Event> events) {
        this.events = events;
    }

    public HashMap<String, Event> getEvents() {
        return events;
    }

    public void addEvent(String eventName, LocalDateTime eventDateStart, LocalDateTime eventDateEnd) {
        if (eventDateEnd.isBefore(eventDateStart)) {
            throw new IllegalArgumentException("End date of event cannot be before start date.");
        }
        Event newEvent = new Event(eventName, eventDateStart, eventDateEnd);
        if (isOverlappingAny(newEvent, events.values())) {
            throw new IllegalArgumentException("Event date cannot overlap other events.");
        }

        if (events.containsKey(eventName)) {
            throw new IllegalArgumentException("There is already an event with this name.");
        }

        events.put(eventName, newEvent);
    }

    public void removeEvent(String eventName) {
        events.remove(eventName);
    }

    public void changeEventDate(String eventName, LocalDateTime newDateStart, LocalDateTime newDateEnd) {
        if (newDateEnd.isBefore(newDateStart)) {
            throw new IllegalArgumentException("End date of event cannot be before start date.");
        }
        Event event = new Event(eventName, newDateStart, newDateEnd);
        if (isOverlappingAny(event, events.values())) {
            throw new IllegalArgumentException("Event date cannot overlap other events.");
        }
        events.put(eventName, event);
    }

    public static boolean isOverlappingAny (Event event, Collection<Event> eventSet) {
        return eventSet.stream().anyMatch(
                e -> event.getDateStart().isBefore(e.getDateEnd()) && event.getDateEnd().isAfter(e.getDateStart())
        );
    }

}
