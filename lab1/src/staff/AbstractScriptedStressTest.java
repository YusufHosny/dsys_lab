package staff;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import hotel.BookingDetail;

public abstract class AbstractScriptedStressTest {

    private final LocalDate today = LocalDate.now();
    protected abstract boolean isRoomAvailable(Integer room, LocalDate date) throws Exception;
    protected abstract void addBooking(BookingDetail bookingDetail) throws Exception;
    protected abstract Set<Integer> getAvailableRooms(LocalDate date) throws Exception;
    protected abstract Set<Integer> getAllRooms() throws Exception;

    public void run() throws Exception {
        isRoomAvailable(101, today);
        getAllRooms();
        BookingDetail bd1 = new BookingDetail("mateo", 101, today);
        addBooking(bd1);
        isRoomAvailable(102, today);
        isRoomAvailable(102, today);
    }

}