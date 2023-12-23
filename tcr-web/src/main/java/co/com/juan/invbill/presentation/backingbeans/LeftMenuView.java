package co.com.juan.invbill.presentation.backingbeans;

import co.com.juan.invbill.delegate.businessdelegate.IConfigDelegate;
import co.com.juan.invbill.exceptions.EntityException;
import co.com.juan.invbill.model.AppMenu;
import co.com.juan.invbill.util.Bundle;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author Juan Felipe
 */
@Named("leftMenu")
@ViewScoped
public class LeftMenuView extends Bundle implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(LeftMenuView.class);
    private final IConfigDelegate configDelegate;
    private MenuModel model;
    private List<DefaultSubMenu> menus;
    private List<DefaultSubMenu> subMenues;
    private List<AppMenu> appMenus;

    @Inject
    public LeftMenuView(IConfigDelegate configDelegate) {
        this.configDelegate = configDelegate;
    }

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();
        menus = new ArrayList<>();
        subMenues = new ArrayList<>();
        appMenus = new ArrayList<>();
        this.initMenu();
    }

    private void initMenu() {
        try {
            appMenus = this.configDelegate.getAppMenus();
            appMenus.stream()
                    .sorted(Comparator.comparing(AppMenu::getOrden))
                    .forEach(this::loadMenu);
        } catch (EntityException e) {
            log.error(ERROR_CONSULTA_MENUES, e);
        }
    }

    private void loadMenu(AppMenu appMenu) {
        if (appMenu.getMenu() == null) {
            this.loadPrincipalMenu(appMenu);
            return;
        }

        if (appMenu.getMenu().getMenu() == null && appMenu.getSalida() == null) {
            this.loadSubMenu(appMenu);
            return;
        }

        this.loadMenuItem(appMenu);
    }

    private void loadPrincipalMenu(AppMenu appMenu) {
        DefaultSubMenu menu = this.buildPrincipalMenu(appMenu);
        menus.add(menu);
        model.addElement(menu);
    }

    private void loadSubMenu(AppMenu appMenu) {
        Optional<DefaultSubMenu> menuOptional = menus.stream()
                .filter(defaultSubMenu -> defaultSubMenu.getId().equalsIgnoreCase(appMenu.getMenu().getIdAppMenu()))
                .findFirst();
        if (menuOptional.isPresent()) {
            DefaultSubMenu subMenu = this.buildSubMenu(appMenu);
            subMenues.add(subMenu);
            menuOptional.get().addElement(subMenu);
        }
    }

    private void loadMenuItem(AppMenu appMenu) {
        Optional<DefaultSubMenu> subMenuOptional = subMenues.stream()
                .filter(defaultSubMenu -> defaultSubMenu.getId().equalsIgnoreCase(appMenu.getMenu().getIdAppMenu()))
                .findFirst();
        if (subMenuOptional.isPresent()) {
            DefaultMenuItem item = this.buildMenuItem(appMenu);
            subMenuOptional.get().addElement(item);
            return;
        }

        Optional<DefaultSubMenu> menuOptional = menus.stream()
                .filter(defaultSubMenu -> defaultSubMenu.getId().equalsIgnoreCase(appMenu.getMenu().getIdAppMenu()))
                .findFirst();
        if (menuOptional.isPresent()) {
            DefaultMenuItem item = this.buildMenuItem(appMenu);
            menuOptional.get().addElement(item);
        }
    }

    private DefaultSubMenu buildPrincipalMenu(AppMenu appMenu) {
        DefaultSubMenu menu = new DefaultSubMenu();
        menu.setId(appMenu.getIdAppMenu());
        menu.setLabel(appMenu.getValor());
        return menu;
    }

    private DefaultSubMenu buildSubMenu(AppMenu appMenu) {
        DefaultSubMenu subMenu = new DefaultSubMenu();
        subMenu.setId(appMenu.getIdAppMenu());
        subMenu.setLabel(appMenu.getValor());
        return subMenu;
    }

    private DefaultMenuItem buildMenuItem(AppMenu appMenu) {
        DefaultMenuItem item = new DefaultMenuItem();
        item.setId(appMenu.getIdAppMenu());
        item.setValue(appMenu.getValor());
        item.setOutcome(appMenu.getSalida());
        item.setIcon(appMenu.getIcono());
        return item;
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

}
