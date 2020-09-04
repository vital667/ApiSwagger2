package ksiazki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String author;

    @Column(name = "category_id")
    private Integer categoryId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="category_id", updatable = false, insertable = false)
    private Category category;

    @JsonIgnore
    private String isbn;


    @Transient
    @JsonIgnore
    private String categoryName;

    @JsonIgnore
    public String getCategoryName() {
        return categoryName;
    }

    @JsonProperty
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }





    @Override
    public String toString() {
        return "id " + id +
                " title " + title +
                " author " + author +
                " categoryId " + categoryId;
    }
}
