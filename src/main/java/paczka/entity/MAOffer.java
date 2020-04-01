package paczka.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class MAOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Brand brand;

    private String location;

    private String street;

    private String description;

    @OneToMany//(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "maoffer", targetEntity = MAOfferDetail.class)
    //@JoinTable(name="maoffer_mod", joinColumns=@JoinColumn(name="maoffer_id"), inverseJoinColumns=@JoinColumn(name="maoffferdetail_id"))
    private List<MAOfferDetail> maOfferDetailList;

    @ManyToMany
    private List<User> clients;

    @OneToMany
    private List<User> coaches;

    @OneToMany
    private List<MAClass> classes;
}
