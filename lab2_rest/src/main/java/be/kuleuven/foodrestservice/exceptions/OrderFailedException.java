package be.kuleuven.foodrestservice.exceptions;

public class OrderFailedException extends RuntimeException {

    public OrderFailedException() {
        super("Could not place order");
    }
}
