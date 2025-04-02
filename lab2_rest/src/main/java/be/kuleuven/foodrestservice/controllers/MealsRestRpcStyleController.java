package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.domain.Order;
import be.kuleuven.foodrestservice.domain.OrderConfirmation;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import be.kuleuven.foodrestservice.exceptions.OrderFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
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

    @GetMapping("/restrpc/cheapestmeal")
    Meal getCheapestMeal() {
        Optional<Meal> meal = mealsRepository.findCheapestMeal();

        return meal.orElseThrow(MealNotFoundException::new);
    }

    @PostMapping("/restrpc/meal")
    void addMeal(Meal meal) {
        mealsRepository.addMeal(meal.getId(), meal);
    }

    @DeleteMapping("/restrpc/meal/{id}")
    void deleteMeal(@PathVariable String id) {
        mealsRepository.deleteMeal(id);
    }

    @GetMapping("/restrpc/largestmeal")
    Meal getLargestMeal() {
        Optional<Meal> meal = mealsRepository.findCheapestMeal();

        return meal.orElseThrow(MealNotFoundException::new);
    }

    @PostMapping("/restrpc/addorder")
    OrderConfirmation addOrder(Order order) {
        OrderConfirmation confirmation = mealsRepository.addOrder(order).orElseThrow(OrderFailedException::new);;

        return confirmation;
    }
}
