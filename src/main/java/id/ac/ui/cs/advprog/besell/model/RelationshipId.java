package id.ac.ui.cs.advprog.besell.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class RelationshipId implements Serializable {
    private String orderId;
    private String listingId;

    public RelationshipId(String orderId, String listingId) {
        this.orderId = orderId;
        this.listingId = listingId;
    }

    private RelationshipId() {

    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        RelationshipId pk = (RelationshipId) o;
        return Objects.equals( orderId, pk.orderId ) &&
                Objects.equals( listingId, pk.listingId );
    }
}
