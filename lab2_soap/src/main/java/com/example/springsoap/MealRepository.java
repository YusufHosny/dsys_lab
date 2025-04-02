package com.example.springsoap;

import javax.annotation.PostConstruct;
import javax.xml.crypto.Data;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.time.LocalDate;
import java.util.*;


import io.foodmenu.gt.webservice.*;


import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MealRepository {
    private static final Map<String, Meal> meals = new HashMap<String, Meal>();

    @PostConstruct
    public void initData() {

        Meal a = new Meal();
        a.setName("Steak");
        a.setDescription("Steak with fries");
        a.setMealtype(Mealtype.MEAT);
        a.setPrice(10.);
        a.setKcal(1100);


        meals.put(a.getName(), a);

        Meal b = new Meal();
        b.setName("Portobello");
        b.setDescription("Portobello Mushroom Burger");
        b.setMealtype(Mealtype.VEGAN);
        b.setPrice(8.);
        b.setKcal(637);


        meals.put(b.getName(), b);

        Meal c = new Meal();
        c.setName("Fish and Chips");
        c.setDescription("Fried fish with chips");
        c.setMealtype(Mealtype.FISH);
        c.setPrice(9.);
        c.setKcal(950);

        meals.put(c.getName(), c);
    }

    public Meal findMeal(String name) {
        Assert.notNull(name, "The meal's code must not be null");
        return meals.get(name);
    }

    public Meal findBiggestMeal() {

        if (meals == null) return null;
        if (meals.size() == 0) return null;

        var values = meals.values();
        return values.stream().max(Comparator.comparing(Meal::getKcal)).orElseThrow(NoSuchElementException::new);

    }

    public Meal findSmallestMeal() {

        if (meals == null) return null;
        if (meals.size() == 0) return null;

        var values = meals.values();
        return values.stream().min(Comparator.comparing(Meal::getKcal)).orElseThrow(NoSuchElementException::new);

    }

    public Meal findCheapestMeal() {

        if (meals == null) return null;
        if (meals.size() == 0) return null;

        var values = meals.values();
        return values.stream().min(Comparator.comparing(Meal::getPrice)).orElseThrow(NoSuchElementException::new);

    }

    public OrderConfirmation addOrder(Order order) {
        if(order.getOrderItems().isEmpty()) return null;
        if(order.getAddress().isEmpty()) return null;

        OrderConfirmation confirmation = new OrderConfirmation();

        confirmation.setTotalprice(
                order.getOrderItems().stream().mapToDouble(
                        item -> findMeal(item.getMealName()).getPrice() * item.getAmount()
                ).sum()
        );


        GregorianCalendar confirmationdate = new GregorianCalendar();
        GregorianCalendar deliverydate = new GregorianCalendar();

        Date today = new Date();

        confirmationdate.setTime(today);
        deliverydate.setTime(new Date(today.getTime() + 2*24*60*60*1000)); // in 2days

        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();

            confirmation.setConfirmationdate(dtf.newXMLGregorianCalendar(confirmationdate));
            confirmation.setDeliverydate(dtf.newXMLGregorianCalendar(deliverydate));

            return confirmation;
        }
        catch(DatatypeConfigurationException dtce) {
            dtce.printStackTrace();
            return null;
        }
    }


}