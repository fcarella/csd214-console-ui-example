package org.byfc.ui;

/**
 * A MenuItem represents the possible kinds of items that can be in a menu, one of:
 * Action - an item that performs an action when selected
 * Menu - an item that contains a submenu of other MenuItems
 */
public sealed interface MenuItem {

    /**
     * An Action represents a menu item that performs an action when selected.
     * @param name The name of the action
     * @param action The action to perform when selected, implemented as a Runnable
     *               (i.e. a lambda expression that takes no arguments and returns no value)
     * Examples:
     *     new Action("Exit", () -> System.exit(0))
     *     new Action("Print Hello", () -> System.out.println("Hello"))
     */
    record Action(String name, Runnable action) implements MenuItem {
        // Augment the constructor to ensure we always have valid Actions to work with
        public Action {
            if ( name == null || name.isBlank() ) {
                throw new IllegalArgumentException("Action name cannot be null or blank");
            }
            if ( action == null ) {
                throw new IllegalArgumentException("Action cannot be null");
            }
        }
    }
    record Menu(String name, MenuItem[] items) implements MenuItem {
        // Augment the constructor to ensure we always have valid Menus to work with
        public Menu {
            if ( name == null || name.isBlank() ) {
                throw new IllegalArgumentException("Menu name cannot be null or blank");
            }
            if ( items == null || items.length == 0 ) {
                throw new IllegalArgumentException("Menu must have at least one item");
            }
        }
    }
}