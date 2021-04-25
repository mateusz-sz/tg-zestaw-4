import java.time.LocalDateTime;
import java.util.Objects;

public class Event {

    private final String name;
    private final LocalDateTime dateStart;
    private final LocalDateTime dateEnd;

    public Event(String name, LocalDateTime dateStart, LocalDateTime dateEnd) {
        if (dateEnd.isBefore(dateStart)) {
            throw new IllegalArgumentException("End date of event cannot be before start date.");
        }
        else {
            this.name = name;
            this.dateStart = dateStart;
            this.dateEnd = dateEnd;
        }
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return name.equals(event.name) && dateStart.equals(event.dateStart) && dateEnd.equals(event.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateStart, dateEnd);
    }
}
