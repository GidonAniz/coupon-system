package com.gidon.gidon_aniz_couponsystem_spring_boot.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = 36, updatable = false, unique = true)
    private UUID uuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @JoinColumn(name = "company_id")
    private Set<Coupon> coupons;
    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp creationTimestamp;
    @UpdateTimestamp
    private Timestamp updateTimestamp;
    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return getUuid().equals(company.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}
