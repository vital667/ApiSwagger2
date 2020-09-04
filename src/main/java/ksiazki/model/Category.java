package ksiazki.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", updatable = false, insertable = false)
    private Integer categoryId;

    private String categoryName;


    @OneToMany(mappedBy = "category")
    private List<Book> books = new ArrayList<>();


//    @Transient
//    @JsonIgnore
//    private String secret;
//
//
//    @JsonIgnore
//    public String getSecret() {
//        return secret;
//    }
//
//    @JsonProperty
//    public void setSecret(String secret) {
//        this.secret = secret;
//    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
