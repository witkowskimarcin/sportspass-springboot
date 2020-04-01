package paczka.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class GymPass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date startDate;

    private Date endDate;

    @OneToOne
    private GymOfferDetail gymOfferDetail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public GymOfferDetail getGymOfferDetail() {
        return gymOfferDetail;
    }

    public void setGymOfferDetail(GymOfferDetail gymOfferDetail) {
        this.gymOfferDetail = gymOfferDetail;
    }
}
