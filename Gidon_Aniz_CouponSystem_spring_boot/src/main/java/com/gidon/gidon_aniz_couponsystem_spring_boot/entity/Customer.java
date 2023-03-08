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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = 36, updatable = false, unique = true)
    private UUID uuid;
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinTable(name = "customer_coupon",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id")
    )
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
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return getUuid().equals(customer.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }

}
