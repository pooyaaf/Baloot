package Baloot.Entity;

import java.io.Serializable;
import java.util.Objects;

public class ExpiredDiscountId implements Serializable {
    private String discountCode;

    private String username;

    public ExpiredDiscountId(String discountCode, String username) {
        this.discountCode = discountCode;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpiredDiscountId that = (ExpiredDiscountId) o;
        return Objects.equals(discountCode, that.discountCode) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountCode, username);
    }
}
