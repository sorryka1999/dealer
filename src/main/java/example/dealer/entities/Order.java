package example.dealer.entities;

import javax.persistence.*;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prototype_id")
    private Prototype prototype;

    private Integer year;
    private String firstname;
    private String lastname;
    private String email;

    public Long getCalcPrice() {
        return (long)(prototype.getPrice().doubleValue()
                * Math.pow(0.9, 2020.0 - year.doubleValue()));
    }

    public String getOrderName() {
        return prototype.getCarBrandName() + "\n"
                + prototype.getCarName() + "\n"
                + prototype.getName();
    }

    public String getPrototypeName() {
        return prototype.getName();
    }

    public Order(Object prototype, Integer year, String firstname, String lastname, String email) {
        this.prototype = (Prototype) prototype;
        this.year = year;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public Object getPrototype() {
        return prototype;
    }

    public void setPrototype(Object prototype) {
        this.prototype = (Prototype) prototype;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
