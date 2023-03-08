package com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDto {
    @JsonProperty("id")
    private UUID uuid;
    private String title;
    private int category;
    private Timestamp startDate;
    private Timestamp endDate;
    private int amount;
    private String description;
    private BigDecimal price;
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CouponDto)) return false;
        CouponDto couponDto = (CouponDto) o;
        return getUuid().equals(couponDto.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}


