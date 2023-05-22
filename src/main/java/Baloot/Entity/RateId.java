package Baloot.Entity;

import java.io.Serializable;
import java.util.Objects;

public class RateId implements Serializable {
    private Integer commodityId;

    private String username;

    public RateId(Integer commodityId, String username) {
        this.commodityId = commodityId;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateId rateId = (RateId) o;
        return Objects.equals(commodityId, rateId.commodityId) && Objects.equals(username, rateId.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commodityId, username);
    }
}