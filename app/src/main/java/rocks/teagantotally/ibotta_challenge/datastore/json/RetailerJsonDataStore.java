package rocks.teagantotally.ibotta_challenge.datastore.json;

import android.content.Context;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.datastore.RetailerDataStore;
import rocks.teagantotally.ibotta_challenge.datastore.models.Retailer;
import rocks.teagantotally.ibotta_challenge.datastore.models.RetailerContainer;
import rocks.teagantotally.ibotta_challenge.events.ErrorEvent;

/**
 * Created by tglenn on 8/30/17.
 */

public class RetailerJsonDataStore
          implements RetailerDataStore {

    @Inject
    Context context;
    @Inject
    EventBus eventBus;

    @Inject
    public RetailerJsonDataStore(Context context,
                                 EventBus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
    }

    private List<Retailer> loadJson() throws
                                      IOException {
        RetailerContainer container = JsonHelper.loadJson(context,
                                                          R.raw.retailers,
                                                          RetailerContainer.class);
        return Arrays.asList(container.retailers);
    }

    @Override
    public List<Retailer> getRetailers(int offset,
                                       int limit) {
        List<Retailer> retailers;
        try {
            retailers = loadJson();
        } catch (IOException e) {
            new ErrorEvent(e).post();
            return null;
        }

        return retailers.subList(offset,
                                 Math.min(retailers.size() - 1,
                                          offset + limit));
    }

    @Override
    public Retailer getRetailerById(long id) {
        List<Retailer> retailers = null;
        try {
            retailers = loadJson();
        } catch (IOException e) {
            new ErrorEvent(e).post();
            return null;
        }

        for (Retailer retailer : retailers) {
            if (retailer.id == id) {
                return retailer;
            }
        }

        return null;
    }

    @Override
    public Retailer getRetailerByName(String name) {
        List<Retailer> retailers;

        try {
            retailers = loadJson();
        } catch (IOException e) {
            new ErrorEvent(e).post();
            return null;
        }

        for (Retailer retailer : retailers) {
            if (TextUtils.equals(retailer.name,
                                 name)) {
                return retailer;
            }
        }

        return null;
    }
}
