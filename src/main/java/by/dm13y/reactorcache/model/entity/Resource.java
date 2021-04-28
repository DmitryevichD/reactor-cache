package by.dm13y.reactorcache.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@Table("data")
@NoArgsConstructor
@AllArgsConstructor
public class Resource implements Persistable<Integer> {
    @Id
    private Integer id;
    private String name;
    @Transient
    private boolean isNew;

    @JsonIgnore
    public String getIdAsString() {
        return String.valueOf(id);
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
