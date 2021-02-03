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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private PrototypeRepo prototypeRepo;

    @Autowired
    private OrderRepo orderRepo;

    @GetMapping
    public String admin(Model model) {
        try {
            Iterable<Brand> brands = brandRepo.findAll();
            model.addAttribute("brands", brands);
            Iterable<Order> orders = orderRepo.findAll();
            model.addAttribute("orders", orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin";
    }

    @PostMapping("addBrand")
    public String addBrand(@RequestParam String brandName) {
        if (brandName == null || brandName.equals("")) {
            return "redirect:/admin";
        }
        try {
            List<Brand> brandList = brandRepo.findByName(brandName);
            if (brandList.isEmpty()) {
                Brand brand = new Brand(brandName, false);
                brandRepo.save(brand);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

    @PostMapping("deleteBrand/{brandId}")
    public String deleteBrand(@PathVariable Long brandId) {
        try {
            Optional<Brand> brand = brandRepo.findById(brandId);
            List<Car> carList = carRepo.findByBrand(brand);
            if (carList.isEmpty()) {
                brandRepo.deleteById(brandId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

    @PostMapping("changeBrandActive/{brandId}")
    public String changeBrandActive(@PathVariable Long brandId, Model model) {
        try {
            Optional<Brand> brand = brandRepo.findById(brandId);
            brand.get().setActive(!brand.get().getActive());
            brandRepo.save(brand.get());
            Optional<Brand> updatedBrand = brandRepo.findById(brandId);
            model.addAttribute("brand", updatedBrand.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fragments/admin :: brandActiveChange";
    }

    @PostMapping("addCar")
    public String addCar(@RequestParam Long brandId,
                         @RequestParam String carName) {
        if (carName == null || carName.equals("") || brandId == null) {
            return "redirect:/admin";
        }
        try {
            List<Car> carList = carRepo.findByName(carName);
            if (carList.isEmpty()) {
                Optional<Brand> brand = brandRepo.findById(brandId);
                Car car = new Car(carName, brand.get(), false);
                carRepo.save(car);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin";

    }

    @PostMapping("deleteCar/{carId}")
    public String deleteCar(@PathVariable Long carId) {
        try {
            Optional<Car> car = carRepo.findById(carId);
            List<Prototype> prototypeList = prototypeRepo.findByCar(car);
            if (prototypeList.isEmpty()) {
                carRepo.deleteById(carId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

    @PostMapping("getCars/{brandId}")
    public String showCars(@PathVariable Long brandId, Model model) {
        try {
            Optional<Brand> brand = brandRepo.findById(brandId);
            Iterable<Car> cars = carRepo.findByBrand(brand);
            model.addAttribute("cars", cars);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fragments/admin :: showCars";
    }

    @PostMapping("changeCarActive/{carId}")
    public String changeCarActive(@PathVariable Long carId, Model model) {
        try {
            Optional<Car> car = carRepo.findById(carId);
            car.get().setActive(!car.get().getActive());
            carRepo.save(car.get());
            Optional<Car> updatedCar = carRepo.findById(carId);
            model.addAttribute("car", updatedCar.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fragments/admin :: carActiveChange";
    }

    @PostMapping("addPrototype")
    public String addPrototype(@RequestParam Long brandId,
                               @RequestParam Long carId,
                               @RequestParam Long prototypePrice,
                               @RequestParam String prototypeName) {
        if (prototypeName == null || prototypeName.equals("")
                || carId == null || prototypePrice == null) {
            return "redirect:/admin";
        }
        try {
            List<Prototype> prototypeList = prototypeRepo.findByName(prototypeName);
            if (prototypeList.isEmpty()) {
                Optional<Car> car = carRepo.findById(carId);
                Prototype prototype =
                        new Prototype(prototypeName, prototypePrice, car.get(), false);
                prototypeRepo.save(prototype);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

    @PostMapping("deletePrototype/{prototypeId}")
    public String deletePrototype(@PathVariable Long prototypeId) {
        try {
            Optional<Prototype> prototype = prototypeRepo.findById(prototypeId);
            List<Order> orderList = orderRepo.findByPrototype(prototype);
            if (orderList.isEmpty()) {
                prototypeRepo.deleteById(prototypeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

    @PostMapping("selectCar/{brandId}")
    public String selectCar(@PathVariable Long brandId, Model model) {
        try {
            Optional<Brand> brand = brandRepo.findById(brandId);
            Iterable<Car> cars = carRepo.findByBrand(brand);
            model.addAttribute("cars", cars);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fragments/admin :: selectCar";
    }

    @PostMapping("getPrototypes/{carId}")
    public String showPrototypes(@PathVariable Long carId, Model model) {
        try {
            Optional<Car> car = carRepo.findById(carId);
            Iterable<Prototype> prototypes = prototypeRepo.findByCar(car);
            model.addAttribute("prototypes", prototypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fragments/admin :: showPrototypes";
    }

    @PostMapping("changePrototypeActive/{prototypeId}")
    public String changePrototypeActive(@PathVariable Long prototypeId, Model model) {
        try {
            Optional<Prototype> prototype = prototypeRepo.findById(prototypeId);
            prototype.get().setActive(!prototype.get().getActive());
            prototypeRepo.save(prototype.get());
            Optional<Prototype> updatedPrototype = prototypeRepo.findById(prototypeId);
            model.addAttribute("prototype", updatedPrototype.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fragments/admin :: prototypeActiveChange";
    }

    @PostMapping("deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        try {
            orderRepo.deleteById(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }
}
