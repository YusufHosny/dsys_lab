package staff;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.Set;

import hotel.BookingDetail;
import hotel.BookingManager;
import hotel.IBookingManager;

public class BookingServer {

    private BookingManager bm = null;

    public static void main(String[] args) {
        BookingServer server = new BookingServer();
        server.run();
    }

    /***************
     * CONSTRUCTOR *
     ***************/
    public BookingServer() {
        try {
            bm = new BookingManager();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public void run() {
        try {
            IBookingManager stub = (IBookingManager) UnicastRemoteObject.exportObject(bm, 0);
            LocateRegistry.getRegistry().bind("BookingManager", stub);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
