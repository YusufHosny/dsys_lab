package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.domain.Order;
import be.kuleuven.foodrestservice.domain.OrderConfirmation;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import be.kuleuven.foodrestservice.exceptions.OrderFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class MealsRestRpcStyleController {

    private final MealsRepository mealsRepository;

    @Autowired
    MealsRestRpcStyleController(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    @GetMapping("/restrpc/meals/{id}")
    Meal getMealById(@PathVariable String id) {
        Optional<Meal> meal = mealsRepository.findMeal(id);

        return meal.orElseThrow(() -> new MealNotFoundException(id));
    }

    @GetMapping("/restrpc/meals")
    Collection<Meal> getMeals() {
        return mealsRepository.getAllMeal();
    }

    @PutMapping(value = "/restrpc/meals/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Meal updateMealById(@PathVariable String id, @RequestBody Meal meal) {
        mealsRepository.deleteMeal(id);
        mealsRepository.addMeal(id, meal);

        return meal;
    }

    @PostMapping(value = "/restrpc/meals", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void addMeal(@RequestBody Meal meal) {
        mealsRepository.addMeal(meal.getId(), meal);
    }

    @DeleteMapping("/restrpc/meal/{id}")
    void deleteMeal(@PathVariable String id) {
        mealsRepository.deleteMeal(id);
    }

    @GetMapping("/restrpc/cheapestmeal")
    Meal getCheapestMeal() {
        Optional<Meal> meal = mealsRepository.findCheapestMeal();

        return meal.orElseThrow(MealNotFoundException::new);
    }

    @GetMapping("/restrpc/largestmeal")
    Meal getLargestMeal() {
        Optional<Meal> meal = mealsRepository.findCheapestMeal();

        return meal.orElseThrow(MealNotFoundException::new);
    }

    @PostMapping(value = "/restrpc/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    OrderConfirmation addOrder(@RequestBody Order order) {
        OrderConfirmation confirmation = mealsRepository.addOrder(order).orElseThrow(OrderFailedException::new);;

        return confirmation;
    }

    @GetMapping("/restrpc/sampleorder")
    Order getSampleOrder() {
        Order o = new Order();
        o.getOrderItems().put("4237681a-441f-47fc-a747-8e0169bacea1", 2);
        o.getOrderItems().put("5268203c-de76-4921-a3e3-439db69c462a", 1);
        o.setAddress("Group T");

        return o;
    }
}
