package Baloot.Entity;

import java.io.Serializable;
import java.util.Objects;

public class PurchasedListId implements Serializable {
    private Integer commodityId;

    private String username;

    public PurchasedListId(Integer commodityId, String username) {
        this.commodityId = commodityId;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasedListId that = (PurchasedListId) o;
        return Objects.equals(commodityId, that.commodityId) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commodityId, username);
    }
}