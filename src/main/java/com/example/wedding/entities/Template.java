package com.example.wedding.entities;

import com.example.wedding.utils.jsonArrayConverter.ComponentConverter;
import com.example.wedding.utils.jsonArrayConverter.JSONArrayConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table(name = "templates", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
})
@Entity()
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "user_id")
    private long userId;

    @Convert(converter = ComponentConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<Component> components;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
