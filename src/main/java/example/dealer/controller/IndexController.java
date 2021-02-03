package example.dealer.controller;

import example.dealer.entities.Brand;
import example.dealer.entities.Car;
import example.dealer.entities.Order;
import example.dealer.entities.Prototype;
import example.dealer.repos.BrandRepo;
import example.dealer.repos.CarRepo;
import example.dealer.repos.OrderRepo;
import example.dealer.repos.PrototypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/home")
public class IndexController {

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private PrototypeRepo prototypeRepo;

    @Autowired
    private OrderRepo orderRepo;

    @GetMapping
    public String home(Model model) {
        try {
            Iterable<Brand> brands = brandRepo.findByActive(true);
            model.addAttribute("brands", brands);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @PostMapping("selectCar/{brandId}")
    public String selectCar(@PathVariable Long brandId, Model model) {
        try {
            Optional<Brand> brand = brandRepo.findById(brandId);
            Iterable<Car> cars = carRepo.findByBrandAndActive(brand, true);
            model.addAttribute("cars", cars);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fragments/index :: selectCar";
    }

    @PostMapping("selectPrototype/{carId}")
    public String selectPrototype(@PathVariable Long carId, Model model) {
        try {
            Optional<Car> car = carRepo.findById(carId);
            Iterable<Prototype> prototypes = prototypeRepo.findByCarAndActive(car, true);
            model.addAttribute("prototypes", prototypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fragments/index :: selectPrototype";
    }

    @PostMapping("showPrice")
    public String showPrice(@RequestParam Long prototypeId,
                            @RequestParam Integer year,
                            Model model) {
        if (prototypeId == null || year == null) {
            return "fragments/index :: showPrice";
        }
        try {
            Optional<Prototype> prototype = prototypeRepo.findById(prototypeId);
            String calcPrice = prototype.get()
                    .getCalcPrice(prototype.get().getPrice(), year).toString();
            model.addAttribute("calcPrice", calcPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fragments/index :: showPrice";
    }

    @PostMapping("addOrder")
    public String addOrder(@RequestParam Long prototypeId,
                           @RequestParam Integer year,
                           @RequestParam String firstname,
                           @RequestParam String lastname,
                           @RequestParam String email) {
        if (prototypeId == null || year == null
                || firstname.equals("") || lastname.equals("")
                || firstname == null || lastname == null) {
            return "redirect:/home";
        }
        try {
            Optional<Prototype> prototype = prototypeRepo.findById(prototypeId);
            Order order = new Order(prototype.get(), year, firstname, lastname, email);
            orderRepo.save(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/home";
    }

}
