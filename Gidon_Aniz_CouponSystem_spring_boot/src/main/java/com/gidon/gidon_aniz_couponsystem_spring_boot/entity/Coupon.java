package com.gidon.gidon_aniz_couponsystem_spring_boot.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
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
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = 36, updatable = false, unique = true)
    private UUID uuid;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinTable(name = "customer_coupon",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customer;
    @Column(nullable = false)
    private int category;
    @Column(nullable = false)
    private Timestamp startDate;
    @Column(nullable = false)
    private Timestamp endDate;
    @Column(nullable = false)
    private int amount;
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    private String imageUrl;
    @CreationTimestamp
    private Timestamp creationTimestamp;
    @UpdateTimestamp
    private Timestamp updateTimestamp;
    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coupon)) return false;
        Coupon coupon = (Coupon) o;
        return getUuid().equals(coupon.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}
