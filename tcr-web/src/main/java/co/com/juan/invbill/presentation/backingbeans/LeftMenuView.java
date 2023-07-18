package co.com.juan.invbill.presentation.backingbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import co.com.juan.invbill.delegate.businessdelegate.IConfigDelegate;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.juan.invbill.delegate.businessdelegate.IClienteDelegate;
import co.com.juan.invbill.model.AppMenu;
import co.com.juan.invbill.util.Properties;

/**
 * @author Juan Felipe
 * 
 */
@ManagedBean(name = "leftMenu")
@ViewScoped
public class LeftMenuView implements Serializable {

	private static final String FILE_MESSAGES = "messages.global";
	private static final long serialVersionUID = -6336619798090876086L;
	private static final Logger log = LoggerFactory.getLogger(LeftMenuView.class);

	@ManagedProperty(value = "#{clienteDelegate}")
	private transient IClienteDelegate clienteDelegate;

	@ManagedProperty(value = "#{configDelegate}")
	private IConfigDelegate configDelegate;

	public IConfigDelegate getConfigDelegate() {
		return configDelegate;
	}

	public void setConfigDelegate(IConfigDelegate configDelegate) {
		this.configDelegate = configDelegate;
	}

	private transient MenuModel model;
	private DefaultSubMenu menu;
	private DefaultSubMenu subMenu;
	private DefaultMenuItem item;
	private List<DefaultSubMenu> menues;
	private List<DefaultSubMenu> subMenues;
	private List<DefaultMenuItem> menuItems;
	private List<AppMenu> appMenus;
	private boolean existeMenu;
	private boolean existeSubMenu;
	private boolean existeMenuItem;
	private transient Properties properties = new Properties(FILE_MESSAGES);

	public LeftMenuView() {
		super();
	}

	@PostConstruct
	public void init() {
		model = new DefaultMenuModel();
		menu = new DefaultSubMenu();
		subMenu = new DefaultSubMenu();
		item = new DefaultMenuItem();
		menues = new ArrayList<>();
		subMenues = new ArrayList<>();
		appMenus = new ArrayList<>();
		menuItems = new ArrayList<>();
		existeMenu = false;
		existeSubMenu = false;
		existeMenuItem = false;
		initMenu();
	}

	public void initMenu() {
		try {
			appMenus = this.configDelegate.getAppMenus();

			// TODO cambio para ordenar
			for (AppMenu appMenu : appMenus.stream()
					.sorted(Comparator.comparing(AppMenu::getOrden))
					.collect(Collectors.toList())) {
				if (appMenu.getMenu() == null) {
					loadMenuPrincipal(appMenu);
				} else {
					if (appMenu.getMenu().getMenu() == null && appMenu.getSalida() == null) {
						loadSubMenu(appMenu);
					} else {
						loadItemsMenu(appMenu);
					}
				}

				existeMenu = false;
				existeSubMenu = false;
				existeMenuItem = false;
			}
		} catch (Exception e) {
			log.error("=== Configuracion del menu: No fue posible cargar los registros", e);
		}
	}

	public void loadMenuPrincipal(AppMenu appMenu) {
		for (DefaultSubMenu menuTemp : menues) {
			if (appMenu.getIdAppMenu().equals(menuTemp.getId())) {
				existeMenu = true;
				break;
			}
		}

		if (!existeMenu) {
			menu = new DefaultSubMenu();
			menu.setId(appMenu.getIdAppMenu());
			menu.setLabel(appMenu.getValor());
			menues.add(menu);
			model.addElement(menu);
		}
	}

	public void loadSubMenu(AppMenu appMenu) {
		for (DefaultSubMenu subMenuTemp : subMenues) {
			if (appMenu.getIdAppMenu().equals(subMenuTemp.getId())) {
				existeSubMenu = true;
				break;
			}
		}

		if (!existeSubMenu) {
			subMenu = new DefaultSubMenu();
			subMenu.setId(appMenu.getIdAppMenu());
			subMenu.setLabel(appMenu.getValor());
			subMenues.add(subMenu);

			loadMenuPrincipal(appMenu.getMenu());
			menu.addElement(subMenu);
		}
	}

	public void loadItemsMenu(AppMenu appMenu) {
		for (DefaultMenuItem menuItemTemp : menuItems) {
			if (appMenu.getIdAppMenu().equals(menuItemTemp.getId())) {
				existeMenuItem = true;
				break;
			}
		}

		if (!existeMenuItem) {
			item = new DefaultMenuItem();
			item.setId(appMenu.getIdAppMenu());
			item.setValue(appMenu.getValor());
			item.setOutcome(appMenu.getSalida());
			item.setIcon(appMenu.getIcono());
			menuItems.add(item);

			if (appMenu.getMenu().getMenu() == null) {
				loadMenuPrincipal(appMenu.getMenu());
				menu.addElement(item);
			} else {
				loadSubMenu(appMenu.getMenu());
				subMenu.addElement(item);
			}
		}
	}

	/**
	 * @return the businessDelegate
	 */
	public IClienteDelegate getClienteDelegate() {
		return clienteDelegate;
	}

	/**
	 * 
	 */
	public void setClienteDelegate(IClienteDelegate clienteDelegate) {
		this.clienteDelegate = clienteDelegate;
	}

	/**
	 * @return the model
	 */
	public MenuModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(MenuModel model) {
		this.model = model;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return the appMenus
	 */
	public List<AppMenu> getAppMenus() {
		return appMenus;
	}

	/**
	 * @param appMenus the appMenus to set
	 */
	public void setAppMenus(List<AppMenu> appMenus) {
		this.appMenus = appMenus;
	}

	/**
	 * @return the menu
	 */
	public DefaultSubMenu getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(DefaultSubMenu menu) {
		this.menu = menu;
	}

	/**
	 * @return the subMenu
	 */
	public DefaultSubMenu getSubMenu() {
		return subMenu;
	}

	/**
	 * @param subMenu the subMenu to set
	 */
	public void setSubMenu(DefaultSubMenu subMenu) {
		this.subMenu = subMenu;
	}

	/**
	 * @return the item
	 */
	public DefaultMenuItem getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(DefaultMenuItem item) {
		this.item = item;
	}

	/**
	 * @return the menues
	 */
	public List<DefaultSubMenu> getMenues() {
		return menues;
	}

	/**
	 * @param menues the menues to set
	 */
	public void setMenues(List<DefaultSubMenu> menues) {
		this.menues = menues;
	}

	/**
	 * @return the subMenues
	 */
	public List<DefaultSubMenu> getSubMenues() {
		return subMenues;
	}

	/**
	 * @return the menuItems
	 */
	public List<DefaultMenuItem> getMenuItems() {
		return menuItems;
	}

	/**
	 * @param menuItems the menuItems to set
	 */
	public void setMenuItems(List<DefaultMenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	/**
	 * @param subMenues the subMenues to set
	 */
	public void setSubMenues(List<DefaultSubMenu> subMenues) {
		this.subMenues = subMenues;
	}

	/**
	 * @return the existeMenu
	 */
	public boolean isExisteMenu() {
		return existeMenu;
	}

	/**
	 * @param existeMenu the existeMenu to set
	 */
	public void setExisteMenu(boolean existeMenu) {
		this.existeMenu = existeMenu;
	}

	/**
	 * @return the existeSubMenu
	 */
	public boolean isExisteSubMenu() {
		return existeSubMenu;
	}

	/**
	 * @param existeSubMenu the existeSubMenu to set
	 */
	public void setExisteSubMenu(boolean existeSubMenu) {
		this.existeSubMenu = existeSubMenu;
	}

	/**
	 * @return the existeMenuItem
	 */
	public boolean isExisteMenuItem() {
		return existeMenuItem;
	}

	/**
	 * @param existeMenuItem the existeMenuItem to set
	 */
	public void setExisteMenuItem(boolean existeMenuItem) {
		this.existeMenuItem = existeMenuItem;
	}

}
