package co.com.juan.tcr.util;

import java.util.Comparator;

import co.com.juan.tcr.model.AppMenu;

/**
 * @author Juan Felipe
 *
 */
public class SortMenu implements Comparator<AppMenu> {

	@Override
	public int compare(AppMenu appMenu1, AppMenu appMenu2) {
		return appMenu1.getOrden().compareToIgnoreCase(appMenu2.getOrden());
	}

}
