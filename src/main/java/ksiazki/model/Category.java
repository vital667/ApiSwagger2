package ksiazki.model;

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
}
