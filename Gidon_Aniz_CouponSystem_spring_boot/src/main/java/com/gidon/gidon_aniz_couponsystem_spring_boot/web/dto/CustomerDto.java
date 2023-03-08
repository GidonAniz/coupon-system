package com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    @JsonProperty("id")
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<CouponDto> coupons;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDto)) return false;
        CustomerDto that = (CustomerDto) o;
        return getUuid().equals(that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}
