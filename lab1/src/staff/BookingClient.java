package staff;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.util.Set;

import hotel.BookingDetail;
import hotel.BookingManager;
import hotel.IBookingManager;

public class BookingClient extends AbstractScriptedSimpleTest {

	private IBookingManager bm = null;

	public static void main(String[] args) throws Exception {
		BookingClient client = new BookingClient();
		client.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/
	public BookingClient() {
		try {
			//Look up the registered remote instance
			Thread.sleep(1000);
			bm = (IBookingManager) LocateRegistry.getRegistry().lookup("BookingManager");
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
