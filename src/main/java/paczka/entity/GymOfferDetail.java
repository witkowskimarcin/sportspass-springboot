package paczka.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GymOfferDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String text;

    private String description;

    private double price;

    private int months;

    @ManyToOne
    private GymOffer gymOffer;
}
