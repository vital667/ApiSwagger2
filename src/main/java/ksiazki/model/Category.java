package ksiazki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @Transient
    @JsonIgnore
    private String title;

    @JsonIgnore
    public String getTitle() {
        return title;
    }

    @JsonProperty
    public void setTitle(String title) {
        this.title = title;
    }

    @Transient
    @JsonIgnore
    private String author;

    @JsonIgnore
    public String getAuthor() {
        return author;
    }

    @JsonProperty
    public void setAuthor(String author) {
        this.author = author;
    }
}