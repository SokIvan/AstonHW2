package entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "age")
    private Integer age;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Конструкторы
    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(String name, String email, Integer age) {
        this();
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public void setAge(Integer age) {
        this.age = age;
    }


    public void setUpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, name='%s', email='%s', age=%d, createdAt=%s, updatedAt=%s}",
                id, name, email, age, createdAt, updatedAt);
    }
}