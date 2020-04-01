package paczka.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class AddBrandForm {

    private String name;
    private String email;
    private MultipartFile image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MultipartFile getImage() {
        return image;
    }

    public String getImageAsString() throws IOException {

        return Base64.getEncoder().encodeToString(image.getBytes());
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
