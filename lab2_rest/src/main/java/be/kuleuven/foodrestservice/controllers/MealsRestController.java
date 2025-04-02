package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.domain.Order;
import be.kuleuven.foodrestservice.domain.OrderConfirmation;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import be.kuleuven.foodrestservice.exceptions.OrderFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.util.*;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class MealsRestController {

    private final MealsRepository mealsRepository;

    @Autowired
    MealsRestController(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    @GetMapping("/rest/meals/{id}")
    EntityModel<Meal> getMealById(@PathVariable String id) {
        Meal meal = mealsRepository.findMeal(id).orElseThrow(() -> new MealNotFoundException(id));

        return mealToEntityModel(id, meal);
    }

    @GetMapping("/rest/meals")
    CollectionModel<EntityModel<Meal>> getMeals() {
        Collection<Meal> meals = mealsRepository.getAllMeal();

        List<EntityModel<Meal>> mealEntityModels = new ArrayList<>();
        for (Meal m : meals) {
            EntityModel<Meal> em = mealToEntityModel(m.getId(), m);
            mealEntityModels.add(em);
        }
        return CollectionModel.of(mealEntityModels,
                linkTo(methodOn(MealsRestController.class).getMeals()).withSelfRel());
    }

    @GetMapping("/rest/cheapestmeal")
    EntityModel<Meal> getCheapestMeal() {
        Meal meal = mealsRepository.findCheapestMeal().orElseThrow(MealNotFoundException::new);

        return mealToEntityModel(meal.getId(), meal);
    }

    @GetMapping("/rest/largestmeal")
    EntityModel<Meal> getLargestMeal() {
        Meal meal = mealsRepository.findLargestMeal().orElseThrow(MealNotFoundException::new);

        return mealToEntityModel(meal.getId(), meal);
    }

    @PostMapping("/rest/addmeal")
    void addMeal(Meal meal) {
        mealsRepository.addMeal(meal.getId(), meal);
    }

    @GetMapping("/rest/deletemeal/{id}")
    void deleteMeal(@PathVariable String id) {
        mealsRepository.deleteMeal(id);
    }

    @PostMapping(value = "/rest/addorder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EntityModel<OrderConfirmation> addOrder(@RequestBody Order order) {
        OrderConfirmation confirmation = mealsRepository.addOrder(order).orElseThrow(OrderFailedException::new);

        return orderConfirmationToEntityModel(confirmation);
    }

    @GetMapping("/rest/sampleorder")
    EntityModel<Order> getSampleOrder() {
        Order o = new Order();
        
        return EntityModel.of(o);
    }

    private EntityModel<Meal> mealToEntityModel(String id, Meal meal) {
        return EntityModel.of(meal,
                linkTo(methodOn(MealsRestController.class).getMealById(id)).withSelfRel(),
                linkTo(methodOn(MealsRestController.class).getMeals()).withRel("rest/meals"));
    }

    private EntityModel<OrderConfirmation> orderConfirmationToEntityModel(OrderConfirmation confirmation) {
        return EntityModel.of(confirmation);
    }

}
