package org.byfc.ui;

import java.util.stream.Stream;

public class Console {

    public static void println(String message) {
        System.out.println(message);
    }

    public static int promptForIntInRange(String prompt, int min, int max) {
        if ( min > max ) {
            throw new IllegalArgumentException("min cannot be greater than max");
        }
        var scanner = new java.util.Scanner(System.in);
        while ( true ) {
            System.out.print(prompt);
            if ( scanner.hasNextInt() ) {
                var value = scanner.nextInt();
                if ( value >= min && value <= max ) {
                    return value;
                } else {
                    System.out.printf("Input must be between %d and %d%n", min, max);
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // discard invalid input
            }
        }
    }

    /**
     * Prompt the user with a menu and handle navigation and selection
     * @param menu The menu to display
     */
    public static void promptMenu(MenuItem.Menu menu) {
        promptMenu(menu, new MenuItem.Menu[] { menu });
    }
    /**
     * Prompt the user with a menu and handle navigation and selection
     * @param menu The menu to display
     * @param breadcrumbs The breadcrumb trail of menus leading to this one (breadcrumbs[0] is the top-level menu)
     */
    public static void promptMenu(MenuItem.Menu menu, MenuItem.Menu[] breadcrumbs) {

        // Convert breadcrumbs to array of strings
        var names = Stream.of(breadcrumbs).map(MenuItem.Menu::name).toList();
        // Show the breadcrumb trail
        System.out.println(String.join(" > ", names) + " >");
        var i = 1;
        // List the menu items
        for ( var item : menu.items() ) {
            var name = switch ( item ) {
                case MenuItem.Action action -> action.name();
                case MenuItem.Menu subMenu -> subMenu.name();
            };
            System.out.printf("%d. %s%n", i, name);
            i++;
        }

        var choice = promptForMenuOption(menu.items());

        // Handle the user's choice
        // Either navigate to a submenu, perform an action, or go back/up/exit/help)
        // In every case except Exit, we re-prompt (recursively) the user with the appropriate menu
        switch (choice) {
            case MenuOption.Exit exit -> {
                // By not making a recursive call, we exit the prompt loop, letting the original caller continue from here
                System.out.println("Exiting...");
            }

            case MenuOption.Help help -> {
                System.out.println("Enter the number of your choice.");
                System.out.println("Enter '<' to go back to the previous menu.");
                System.out.println("Enter '^' to go back to the main menu.");
                System.out.println("Enter '?' to display this help message.");
                System.out.println("Enter 'x' or 'exit' to exit.");
                // Re-prompt the user with the same menu
                promptMenu(menu, breadcrumbs);
            }

            // Re-prompt the user with the top-level menu
            case MenuOption.Top top -> promptMenu(breadcrumbs[0], new MenuItem.Menu[] { breadcrumbs[0] });

            case MenuOption.Back back -> {
                if ( breadcrumbs.length == 1 ) {
                    System.out.println("Already at main menu.");
                    // Just re-prompt the user with the same menu since we're already at the top
                    promptMenu(menu, breadcrumbs);
                } else {
                    // Otherwise, go back to the previous menu in the breadcrumb trail
                    var newCrumbs = new MenuItem.Menu[breadcrumbs.length - 1];
                    System.arraycopy(breadcrumbs, 0, newCrumbs, 0, breadcrumbs.length - 1);
                    promptMenu(breadcrumbs[breadcrumbs.length - 2], newCrumbs);
                }
            }

            case MenuOption.Selected selected -> {
                switch (selected.item()) {
                    case MenuItem.Action action -> {
                        action.action().run();
                        // After performing the action, re-prompt the user with the top-level menu
                        promptMenu(breadcrumbs[0], new MenuItem.Menu[] { breadcrumbs[0] });
                    }
                    case MenuItem.Menu subMenu -> {
                        // Re-prompt the user with the selected submenu, adding it to the breadcrumb trail
                        var newCrumbs = new MenuItem.Menu[breadcrumbs.length + 1];
                        System.arraycopy(breadcrumbs, 0, newCrumbs, 0, breadcrumbs.length);
                        newCrumbs[breadcrumbs.length] = subMenu;
                        promptMenu(subMenu, newCrumbs);
                    }
                }
            }
        }
    }


    /**
     * MenuOption represents the possible inputs the user can provide at a menu prompt
     */
    private sealed interface MenuOption {

        /**
         * Selected indicates the user selected a valid menu item
         * @param item The selected menu item
         */
        record Selected(MenuItem item) implements MenuOption {}
        /**
         * Back indicates the user wants to go back to the previous menu
         */
        record Back() implements MenuOption {}

        /**
         * Top indicates the user wants to go back to the top-level menu
         */
        record Top() implements MenuOption {}

        /**
         * Exit indicates the user wants to exit the menu
         */
        record Exit() implements MenuOption {}

        /**
         * Help indicates the user wants to see the help message
         */
        record Help() implements MenuOption {}
    }


    /**
     * Prompt the user to select a menu option from the given items
     * @param items The current menu items to choose from
     * @return The user's selected menu option
     */
    private static MenuOption promptForMenuOption(MenuItem[] items) {
        System.out.print("Enter choice: ");
        var scanner = new java.util.Scanner(System.in);

        while (true) {
            // If the input is an int, the user is attempting to select a menu item by number
            if ( scanner.hasNextInt() ) {
                var choice = scanner.nextInt();
                // Ensure that the choice is within the valid range
                if ( choice >= 1 && choice <= items.length ) {
                    return new MenuOption.Selected(items[choice - 1]);
                } else {
                    System.out.printf("Choice must be between 1 and %d%n", items.length);
                }
            } else {
                // Otherwise, the user is attempting to navigate the menu or get help/exit
                var input = scanner.next();
                if ( input.equals("<") ) {
                    return new MenuOption.Back();
                } else if ( input.equals("^") ) {
                    return new MenuOption.Top();
                } else if ( input.equals("?") ) {
                    return new MenuOption.Help();
                } else if ( input.equals("exit") || input.equals("x") ) {
                    return new MenuOption.Exit();
                } else {
                    System.out.println("Invalid input. Please enter a number, or '?' for help.");
                }
            }
        }
    }

}
