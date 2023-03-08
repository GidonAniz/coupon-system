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
public class CompanyDto {
    @JsonProperty("id")
    private UUID uuid;
    private String name;
    private String email;
    private String password;
    private List<CouponDto> coupons;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDto)) return false;
        CompanyDto that = (CompanyDto) o;
        return getUuid().equals(that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}
