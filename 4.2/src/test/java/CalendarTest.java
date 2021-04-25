import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CalendarTest {

    private Calendar calendar;

    private static final String ZOSIA_NAME = "Zosia";
    private static final String ZOSIA_NAME_2 = "Zosia2";
    private static final LocalDateTime ZOSIA_BEGIN_DATE = LocalDateTime.now().plusMonths(1);
    private static final LocalDateTime ZOSIA_END_DATE = LocalDateTime.now().plusMonths(1).plusHours(2);

    private static final LocalDateTime ZOSIA_2_BEGIN_DATE = LocalDateTime.now().plusMonths(2);
    private static final LocalDateTime ZOSIA_2_END_DATE = LocalDateTime.now().plusMonths(2).plusDays(1).plusHours(2);

    private static final LocalDateTime ZOSIA_3_BEGIN_DATE = LocalDateTime.now().plusMonths(3).plusDays(1);
    private static final LocalDateTime ZOSIA_3_END_DATE = LocalDateTime.now().plusMonths(3).plusDays(1).plusHours(3);

    private static final LocalDateTime ZOSIA_4_BEGIN_DATE = LocalDateTime.now();
    private static final LocalDateTime ZOSIA_4_END_DATE = LocalDateTime.now().plusMinutes(30).plusHours(2);

    private static final LocalDateTime ZOSIA_5_BEGIN_DATE = LocalDateTime.now().plusMonths(4).plusDays(13);
    private static final LocalDateTime ZOSIA_5_END_DATE = LocalDateTime.now().plusMonths(4).plusDays(14).plusHours(2);


    @BeforeEach
    void setUp() {
        HashMap<String, Event> events = new HashMap<>();

        events.put(ZOSIA_NAME, new Event(ZOSIA_NAME, ZOSIA_BEGIN_DATE, ZOSIA_END_DATE));
        events.put(ZOSIA_NAME_2, new Event(ZOSIA_NAME_2, ZOSIA_2_BEGIN_DATE, ZOSIA_2_END_DATE));
        events.put("Zosia3", new Event("Zosia3", ZOSIA_3_BEGIN_DATE, ZOSIA_3_END_DATE));
        events.put("Zosia4", new Event("Zosia4", ZOSIA_4_BEGIN_DATE, ZOSIA_4_END_DATE));
        events.put("Zosia5", new Event("Zosia5", ZOSIA_5_BEGIN_DATE, ZOSIA_5_END_DATE));

        calendar = new Calendar(events);
    }

    @Test
    void shouldAddEventToCalendar() {
        final String NEW_NAME = "newName";
        calendar.addEvent(
                NEW_NAME,
                ZOSIA_5_END_DATE,
                ZOSIA_5_END_DATE.plusHours(2)
        );
        Event addedEvent = new Event(NEW_NAME, ZOSIA_5_END_DATE, ZOSIA_5_END_DATE.plusHours(2));

        assertAll(
                () -> assertTrue(calendar.getEvents().containsKey(NEW_NAME)),
                () -> assertEquals(addedEvent, calendar.getEvents().get(NEW_NAME))
        );
    }

    @Test
    void shouldRemoveEventFromCalendar() {
        calendar.removeEvent(ZOSIA_NAME);
        assertFalse(calendar.getEvents().containsKey(ZOSIA_NAME));
    }

    @Test
    void shouldThrowExceptionWhenEndDateIsBeforeStartDate() {
        assertThrows(IllegalArgumentException.class, () ->
                calendar.addEvent(
                        "eventName",
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now()
                ));
    }

    @Test
    void shouldThrowExceptionWhenAddingEventOverlappingWithExistingOne() {
        LocalDateTime newEventStartDate1 = LocalDateTime.now().plusWeeks(2);
        LocalDateTime newEventEndDate1 = LocalDateTime.now().plusMonths(1).plusHours(1);
        LocalDateTime newEventStartDate2 = LocalDateTime.now().plusMonths(1).plusHours(1);
        LocalDateTime newEventEndDate2 = LocalDateTime.now().plusMonths(1).plusHours(3);
        LocalDateTime newEventStartDate3 = LocalDateTime.now().plusMonths(1).plusHours(1);
        LocalDateTime newEventEndDate3 = LocalDateTime.now().plusMonths(1).plusHours(1).plusMinutes(30);
        LocalDateTime newEventStartDate4 = LocalDateTime.now().plusWeeks(2);
        LocalDateTime newEventEndDate4 = LocalDateTime.now().plusMonths(1).plusDays(1);

        assertAll(
                () ->
                        assertThrows(IllegalArgumentException.class, () ->
                                calendar.addEvent("newName", newEventStartDate1, newEventEndDate1)),
                () ->
                        assertThrows(IllegalArgumentException.class, () ->
                                calendar.addEvent("newName", newEventStartDate2, newEventEndDate2)),
                () ->
                        assertThrows(IllegalArgumentException.class, () ->
                                calendar.addEvent("newName", newEventStartDate3, newEventEndDate3)),
                () ->
                        assertThrows(IllegalArgumentException.class, () ->
                                calendar.addEvent("newName", newEventStartDate4, newEventEndDate4))
        );
    }

    @Test
    @DisplayName("Should throw exception, when trying to add event with name, which already exists in calendar")
    void shouldThrowExceptionWhenAddingEventWithAlreadyExistingName() {
        String sameName = "newName";
        calendar.addEvent(
                sameName,
                LocalDateTime.now().plusYears(10),
                LocalDateTime.now().plusYears(10).plusHours(2)
        );

        assertThrows(IllegalArgumentException.class, () ->
                calendar.addEvent(
                        sameName,
                        LocalDateTime.now().plusYears(11),
                        LocalDateTime.now().plusYears(11).plusHours(2)
                ));
    }

    @Test
    void shouldThrowExceptionWhenNewEventDateIsNotValid() {
        assertThrows(IllegalArgumentException.class, () -> {
            LocalDateTime dateStart = LocalDateTime.now().plusYears(1).plusWeeks(10);
            LocalDateTime dateEnd = LocalDateTime.now().plusYears(1);
            calendar.changeEventDate(ZOSIA_NAME, dateStart, dateEnd);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            calendar.changeEventDate(ZOSIA_NAME, ZOSIA_BEGIN_DATE, ZOSIA_2_BEGIN_DATE.plusDays(1));
        });
    }
}