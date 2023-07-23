package org.benevity.server.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Table(name = "new")
public class NewFile {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    @NotNull
    private String name;
}
