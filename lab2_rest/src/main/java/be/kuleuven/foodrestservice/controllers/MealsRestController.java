package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.domain.Order;
import be.kuleuven.foodrestservice.domain.OrderConfirmation;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import be.kuleuven.foodrestservice.exceptions.OrderFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
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

    @PutMapping(value = "/rest/meals/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EntityModel<Meal> updateMealById(@PathVariable String id, @RequestBody Meal meal) {
        mealsRepository.deleteMeal(id);
        mealsRepository.addMeal(id, meal);

        return mealToEntityModel(id, meal);
    }
    @PostMapping(value = "/rest/meals/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EntityModel<Meal> addMealById(@PathVariable String id, @RequestBody Meal meal) {
        meal.setId(id);
        mealsRepository.addMeal(id, meal);

        return mealToEntityModel(id, meal);
    }

    @PostMapping(value = "/rest/meals", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EntityModel<Meal> addMeal(@RequestBody Meal meal) {
        meal.setId(String.valueOf(UUID.randomUUID()));
        mealsRepository.addMeal(meal.getId(), meal);

        return mealToEntityModel(meal.getId(), meal);
    }

    @DeleteMapping("/rest/meals/{id}")
    void deleteMealById(@PathVariable String id) {
        mealsRepository.deleteMeal(id);
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

    @PostMapping(value = "/rest/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EntityModel<OrderConfirmation> addOrder(@RequestBody Order order) {
        OrderConfirmation confirmation = mealsRepository.addOrder(order).orElseThrow(OrderFailedException::new);

        return orderConfirmationToEntityModel(confirmation);
    }

    @GetMapping("/rest/sampleorder")
    EntityModel<Order> getSampleOrder() {
        Order o = new Order();
        o.getOrderItems().put("4237681a-441f-47fc-a747-8e0169bacea1", 2);
        o.getOrderItems().put("5268203c-de76-4921-a3e3-439db69c462a", 1);
        o.setAddress("Group T");
        
        return orderToEntityModel(o);
    }

    private EntityModel<Meal> mealToEntityModel(String id, Meal meal) {
        return EntityModel.of(meal,
                linkTo(methodOn(MealsRestController.class).getMealById(id)).withSelfRel(),
                linkTo(methodOn(MealsRestController.class).getMeals()).withRel("rest/meals"));
    }

    private EntityModel<OrderConfirmation> orderConfirmationToEntityModel(OrderConfirmation confirmation) {
        return EntityModel.of(confirmation);
    }

    private EntityModel<Order> orderToEntityModel(Order order) {
        Links links = order.getOrderItems().keySet().stream().map(
                id -> linkTo(methodOn(MealsRestController.class).getMealById(id)).withSelfRel()
        ).collect(Links.collector());
        return EntityModel.of(order, links);
    }

}
