package hotel;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BookingManager implements IBookingManager {

	private Room[] rooms;

	public BookingManager() {
		this.rooms = initializeRooms();
	}

	public Set<Integer> getAllRooms() throws RemoteException {
		Set<Integer> allRooms = new HashSet<Integer>();
		Iterable<Room> roomIterator = Arrays.asList(rooms);
		for (Room room : roomIterator) {
			allRooms.add(room.getRoomNumber());
		}
		return allRooms;
	}

	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) throws RemoteException {
		Optional<Room> room = Arrays.stream(rooms).filter(r -> Objects.equals(r.getRoomNumber(), roomNumber)).findFirst();
		return room.map(value -> value.getBookings().stream().allMatch(b -> !b.getDate().equals(date))).orElse(false);
	}

	public void addBooking(BookingDetail bookingDetail) throws RemoteException {
		Optional<Room> room = Arrays.stream(rooms).filter(r -> Objects.equals(r.getRoomNumber(), bookingDetail.getRoomNumber())).findFirst();
		room.map(value -> value.getBookings().add(bookingDetail));
	}

	public Set<Integer> getAvailableRooms(LocalDate date) throws RemoteException {
		return Arrays.stream(rooms).filter(r -> r.getBookings().stream().allMatch(b -> !b.getDate().equals(date))).map(Room::getRoomNumber).collect(Collectors.toSet());
	}

	private static Room[] initializeRooms() {
		Room[] rooms = new Room[4];
		rooms[0] = new Room(101);
		rooms[1] = new Room(102);
		rooms[2] = new Room(201);
		rooms[3] = new Room(203);
		return rooms;
	}
}
