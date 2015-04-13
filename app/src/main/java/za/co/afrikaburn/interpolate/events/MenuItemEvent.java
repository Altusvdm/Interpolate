package za.co.afrikaburn.interpolate.events;

import android.view.MenuItem;

/**
 * Created by Altus on 2015/04/13.
 */
public class MenuItemEvent {
    MenuItem menuItem;

    public MenuItemEvent(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getId() {
        return this.menuItem.getItemId();
    }
}
