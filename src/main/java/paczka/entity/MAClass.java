package paczka.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Data
@ToString
public class MAClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String description;

    @ManyToOne
    private MAOffer maOffer;

    private int numberOfDay;

    private Time time;

    @OneToOne
    private User coach;
}
