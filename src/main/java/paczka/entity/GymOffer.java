package paczka.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class GymOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Brand brand;

    private String location;

    private String street;

    private String description;

    @OneToMany
    private List<GymOfferDetail> gymOfferDetailList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<GymOfferDetail> getGymOfferDetailList() {
        return gymOfferDetailList;
    }

    public void setGymOfferDetailList(List<GymOfferDetail> gymOfferDetailList) {
        this.gymOfferDetailList = gymOfferDetailList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
