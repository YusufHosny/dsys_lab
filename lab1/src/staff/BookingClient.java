package staff;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.util.Set;

import hotel.BookingDetail;
import hotel.BookingManager;
import hotel.IBookingManager;

public class BookingClient extends AbstractScriptedStressTest {

	private IBookingManager bm = null;

	// arg 0: hostname
	public static void main(String[] args) throws Exception {
		BookingClient client = new BookingClient(args[0]);
		client.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/
	public BookingClient(String host) {
		try {
			bm = (IBookingManager) LocateRegistry.getRegistry(host).lookup("BookingManager");
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
		while(true) {
			try {
				return bm.isRoomAvailable(roomNumber, date);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addBooking(BookingDetail bookingDetail) {
		while(true) {
			try {
				bm.addBooking(bookingDetail);
				return;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Set<Integer> getAvailableRooms(LocalDate date) {
		while(true) {
			try {
				return bm.getAvailableRooms(date);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Set<Integer> getAllRooms() {
		while(true) {
			try {
				return bm.getAllRooms();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
