package paczka.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MAPass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date startDate;

    private Date endDate;

    private int entries;

    @OneToOne
    private MAOfferDetail maOfferDetail;

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

    public int getEntries() {
        return entries;
    }

    public void setEntries(int entries) {
        this.entries = entries;
    }

    public MAOfferDetail getMaOfferDetail() {
        return maOfferDetail;
    }

    public void setMaOfferDetail(MAOfferDetail maOfferDetail) {
        this.maOfferDetail = maOfferDetail;
    }
}
