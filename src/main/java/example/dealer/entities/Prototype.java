package example.dealer.entities;

import javax.persistence.*;

@Entity
public class Prototype {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Long price;
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    public Long getCalcPrice(Long price, Integer year) {
        return (long)(price.doubleValue() * Math.pow(0.9, 2020.0 - year.doubleValue()));
    }

    public String getCarName() {
        return car.getName();
    }

    public String getCarBrandName() {
        return car.getBrandName();
    }

    public Prototype() {
    }

    public Prototype(String name, Long price, Object car, Boolean active) {
        this.name = name;
        this.price = price;
        this.car = (Car) car;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Object car) {
        this.car = (Car) car;
    }
}
