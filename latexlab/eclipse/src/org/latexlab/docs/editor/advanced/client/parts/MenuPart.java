package org.latexlab.docs.editor.advanced.client.parts;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import org.latexlab.docs.client.content.menus.DynamicCompilerMenu;
import org.latexlab.docs.client.content.menus.DynamicEditMenu;
import org.latexlab.docs.client.content.menus.DynamicExportMenu;
import org.latexlab.docs.client.content.menus.DynamicFileMenu;
import org.latexlab.docs.client.content.menus.DynamicFormatMenu;
import org.latexlab.docs.client.content.menus.DynamicHelpMenu;
import org.latexlab.docs.client.content.menus.DynamicInsertMenu;
import org.latexlab.docs.client.content.menus.DynamicMathMenu;
import org.latexlab.docs.client.content.menus.DynamicViewMenu;
import org.latexlab.docs.client.events.CommandEvent;
import org.latexlab.docs.client.events.CommandHandler;
import org.latexlab.docs.client.events.HasCommandHandlers;
import org.latexlab.docs.client.widgets.ExtendedMenuBar;

import java.util.HashMap;

/**
 * A specialized, non-reusable widget containing the main menu bar.
 */
public class MenuPart extends Composite implements HasCommandHandlers {
	
  private HashMap<String, MenuItem> itemIndex;
  private HandlerManager manager;
  private ExtendedMenuBar menu;
  
  /**
   * Constructs a Menu part.
   */
  public MenuPart() {
    manager = new HandlerManager(this);
    itemIndex = new HashMap<String, MenuItem>();
    VerticalPanel menuPanel = new VerticalPanel();
    menuPanel.setWidth("100%");
    menuPanel.setHeight("30px");
    menuPanel.add(buildMenu());
    menuPanel.setStylePrimaryName("lab-Menu-Panel");
    initWidget(menuPanel);
  }
  
  @Override
  public HandlerRegistration addCommandHandler(CommandHandler handler) {
	return manager.addHandler(CommandEvent.getType(), handler);
  }
 
  /**
   * Builds the main menu bar.
   * 
   * @return the main menu bar
   */
  private ExtendedMenuBar buildMenu() {
    menu = new ExtendedMenuBar(false, this);
    this.menu.addItem("File", DynamicFileMenu.get(this));
    this.menu.addItem("Edit", DynamicEditMenu.get(this));
    this.menu.addItem("View", DynamicViewMenu.get(this));
    this.menu.addItem("Export", DynamicExportMenu.get(this));
    this.menu.addItem("Insert", DynamicInsertMenu.get(this));
    this.menu.addItem("Math", DynamicMathMenu.get(this));
    this.menu.addItem("Format", DynamicFormatMenu.get(this));
    this.menu.addItem("Compiler", DynamicCompilerMenu.get(this));
    this.menu.addItem("Help", DynamicHelpMenu.get(this));
    return menu;
  }
  
  /**
   * Closes the menu.
   */
  public void close() {
	this.menu.close();
  }

  @Override
  public void fireEvent(GwtEvent<?> event) {
	manager.fireEvent(event);
  }
  
  /**
   * Retrieves the root menu bar.
   * @return
   */
  public MenuBar getMenuBar() {
	return this.menu;
  }
  
  /**
   * Retrieves a menu item by title.
   * 
   * @param title the title of the menu item which to retrieve
   * @return the menu item with the specified title
   */
  public MenuItem getMenuItem(String title) {
    return itemIndex.get(title);
  }

  /**
   * Sets the enabled state of a menu item, by title.
   * 
   * @param title the menu item's title
   * @param enabled whether the item is enabled
   */
  public void setMenuItemEnabled(String title, boolean enabled) {
    MenuItem item = getMenuItem(title);
    if (item != null) {
      if (enabled){
    	item.removeStyleDependentName("Disabled");
      } else {
    	item.addStyleDependentName("Disabled");
      }
    }
  }
  
  /**
   * Sets the icon for a menu item, by title.
   * @param title the title of the menu item for which to set the icon image
   * @param icon the icon image
   */
  public void setMenuItemIcon(String title, AbstractImagePrototype icon) {
    MenuItem item = getMenuItem(title);
    if (item != null) {
      item.setHTML(icon.getHTML() + " " + title);
    }
  }
  
}