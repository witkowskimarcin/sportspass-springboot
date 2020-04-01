package paczka.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MAOfferDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String text;

    private String description;

    private int entries;

    private double price;

    @ManyToOne//(fetch=FetchType.LAZY)
    //@JoinColumn(name = "mod_id",referencedColumnName="id")
    private MAOffer maOffer;
}
