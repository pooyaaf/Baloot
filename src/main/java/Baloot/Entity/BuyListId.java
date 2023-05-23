package Baloot.Entity;

import java.io.Serializable;
import java.util.Objects;

public class BuyListId implements Serializable {
    private Integer commodityId;

    private String username;

    public BuyListId(Integer commodityId, String username) {
        this.commodityId = commodityId;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyListId buyListId = (BuyListId) o;
        return Objects.equals(commodityId, buyListId.commodityId) && Objects.equals(username, buyListId.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commodityId, username);
    }
}