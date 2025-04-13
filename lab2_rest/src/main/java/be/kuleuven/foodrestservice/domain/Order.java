package be.kuleuven.foodrestservice.domain;

import java.util.HashMap;
import java.util.Map;

public class Order {

    private String address;
    private Map<String, Integer> orderItems;

    public Order() {
        address = "";
        orderItems = new HashMap<>();
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Integer> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Map<String, Integer> orderItems) {
        this.orderItems = orderItems;
    }


}
