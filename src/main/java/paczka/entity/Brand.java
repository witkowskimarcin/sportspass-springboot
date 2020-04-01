package paczka.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;

@Entity
@Data
public class Brand{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Lob
    private byte[] image;

    @OneToOne
    private User owner;

    @OneToMany
    private List<GymOffer> gymOfferList;

    @OneToMany
    private List<MAOffer> maOfferList;

    public void setImageBase64(String image){
        this.image = Base64.getDecoder().decode(image);
    }

    public String getImage() {

        return Base64.getEncoder().encodeToString(image);
    }
}
