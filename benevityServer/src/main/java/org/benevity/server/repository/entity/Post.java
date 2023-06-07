package org.benevity.server.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Clob;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String title;
    @NotNull
    @Lob
    private String content;
    @NotNull
    private String slug;
    @NotNull
    private LocalDateTime date;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    private String imageUrl;
}
