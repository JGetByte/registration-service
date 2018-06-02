package com.job.piyawut.registration.model;

import com.job.piyawut.registration.constant.MemberType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    @Pattern(regexp = "[0-9]+", message = "Numeric only")
    private String phone;

    @NotNull
    @Column(nullable = false)
    private Double salary;

    @Column(name = "ref_code")
    private String refCode;

    @Column(name = "type")
    private MemberType type;

    @PrePersist
    public void prePersist() {
        this.password = new BCryptPasswordEncoder().encode(this.password);
    }
}
