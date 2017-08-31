package rocks.teagantotally.ibotta_challenge.events.datastore;

import rocks.teagantotally.ibotta_challenge.events.BaseEvent;

/**
 * Created by tglenn on 8/30/17.
 */

public class RetrieveStoresEvent
          extends BaseEvent {
    private long retailerId;

    public RetrieveStoresEvent(long retailerId) {
        this.retailerId = retailerId;
    }

    public long getRetailerId() {
        return retailerId;
    }
}
