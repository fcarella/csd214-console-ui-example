package org.byfc;


import org.byfc.ui.Console;
import org.byfc.ui.MenuItem;

public class Main {

    public static void main(String[] args) {

        var counter = new Counter();

        // Build the menu structure
        var menu = new MenuItem.Menu("Main Menu", new MenuItem[] {
            new MenuItem.Menu("Add Items",new MenuItem[] {
                new MenuItem.Action("Add Book", () -> Console.println("TODO: Add Book")),
                new MenuItem.Action("Add Magazine", () -> Console.println("TODO: Add Magazine")),
                new MenuItem.Action("Add DiscMag", () -> Console.println("TODO: Add DiscMag")),
                new MenuItem.Action("Add Ticket", () -> Console.println("TODO: Add Ticket")),
            }),
            new MenuItem.Action("Edit Items", () -> Console.println("TODO: Edit Items")),
            new MenuItem.Action("Delete Items", () -> Console.println("TODO: Delete Items")),
            new MenuItem.Action("Sell Item(s)", () -> Console.println("TODO: Sell Item(s)")),

            // Example of deeply-nested menu structure and using lambdas to implement actions
            new MenuItem.Menu("Counter", new MenuItem[] {
                new MenuItem.Action("Show count", () -> System.out.println("Current count: " + counter.getCount())),
                new MenuItem.Menu("Increment by...", new MenuItem[] {
                        new MenuItem.Action("1", counter::increment),
                        new MenuItem.Action("10", () -> counter.incrementBy(10)),
                        new MenuItem.Action("100", () -> counter.incrementBy(100)),
                        new MenuItem.Action("Other...", () -> {
                            counter.incrementBy(Console.promptForIntInRange("Enter increment value: ", 0, Integer.MAX_VALUE));
                        }),
                }),
                new MenuItem.Action("Reset", counter::reset),
            })
        });

        Console.promptMenu(menu);
    }
}
