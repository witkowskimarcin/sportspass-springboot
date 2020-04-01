package paczka.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "CanceledClass")
public class CanceledClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Date date;

    @OneToOne
    private MAClass maClass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MAClass getMaClass() {
        return maClass;
    }

    public void setMaClass(MAClass maClass) {
        this.maClass = maClass;
    }
}
