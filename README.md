# Console UI Application

This project is a simple, yet effective, demonstration of a console-based user interface in Java. It showcases a hierarchical menu system that allows users to navigate through different options and perform actions.

### >Credits:  
>Rodney Martin

## How the Code Works

The application is structured into three main packages: `org.byfc`, `org.byfc.ui`, and the default package containing `pom.xml`.

### `pom.xml`

This file is the Project Object Model (POM) for Maven, the build automation tool used for this project. It defines the project's coordinates (groupId, artifactId, version), and its dependencies. It also specifies the Java version to be used for compiling the source and target code, which in this case is Java 24.

### `org.byfc.Counter.java`

This is a simple class that models a counter. It has methods to increment the count, increment by a specific number, reset the count, and get the current count. This class is used in the `Main.java` file to demonstrate how to integrate business logic with the UI.

### `org.byfc.Main.java`

This is the main entry point of the application. It creates a `Counter` object and then builds a menu structure using the `MenuItem` class. The menu is then displayed to the user using the `Console.promptMenu()` method.

### `org.byfc.ui.Console.java`

This class is responsible for all the console input and output. It has methods to print messages to the console, prompt the user for an integer within a certain range, and to display and handle the menu. The `promptMenu()` method is the core of the UI, as it recursively prompts the user with the appropriate menu based on their selection.

### `org.byfc.ui.MenuItem.java`

This file defines the structure of the menu. It uses a `sealed interface` called `MenuItem` which can be either an `Action` or a `Menu`.

*   An `Action` represents a menu item that performs an action when selected.
*   A `Menu` represents a menu item that contains a submenu of other `MenuItem`s.

This structure allows for the creation of a hierarchical menu system of any depth.

## Java Constructs Review

This project makes use of several modern Java constructs that are worth reviewing for students.

### Records

Records, introduced in Java 14, are a new kind of class that are designed to be transparent carriers for immutable data. They are a concise way to create data classes, as the compiler automatically generates the constructor, getters, `equals()`, `hashCode()`, and `toString()` methods.

In this project, `MenuItem.Action` and `MenuItem.Menu` are defined as records. For example, the `Action` record is defined as:

```java
record Action(String name, Runnable action) implements MenuItem { ... }
```

This one line of code is equivalent to a traditional class with private final fields for `name` and `action`, a constructor to initialize them, and getter methods for both.

### Lambdas

Lambda expressions, introduced in Java 8, are a short block of code that takes in parameters and returns a value. They are similar to methods, but they do not need a name and can be implemented right in the body of a method. Lambdas are often used to implement functional interfaces, which are interfaces with a single abstract method.

In this project, lambdas are used to define the actions for the menu items. For example, the "Add Book" menu item is defined as:

```java
new MenuItem.Action("Add Book", () -> Console.println("TODO: Add Book")),
```

The `() -> Console.println("TODO: Add Book")` is a lambda expression that implements the `Runnable` interface's `run()` method.

### Method References

Method references are a shorthand syntax for a lambda expression that contains just one method call. They are used to refer to a method without invoking it. There are four types of method references: static methods, instance methods of a particular object, instance methods of an arbitrary object of a particular type, and constructors.

In this project, a method reference is used for the "Increment by 1" menu item:

```java
new MenuItem.Action("1", counter::increment),
```

The `counter::increment` is a method reference to the `increment()` method of the `counter` object. This is equivalent to the lambda expression `() -> counter.increment()`.

### Sealed Interfaces

Sealed interfaces, a feature introduced in Java 15, allow you to control which classes can implement an interface. This is useful for creating a closed set of related types, which can make your code more predictable and easier to maintain.

In this project, the `MenuItem` interface is sealed:

```java
public sealed interface MenuItem permits Action, Menu { ... }
```

This means that only the `Action` and `Menu` records are allowed to implement the `MenuItem` interface. This ensures that the menu system is composed of only these two types of items.

### Switch Expressions

Switch expressions, a standard feature since Java 14, are an enhanced version of the traditional switch statement. They can be used to return a value, and they use a more concise syntax with the `->` operator.

In this project, a switch expression is used in the `Console.promptMenu()` method to get the name of a menu item:

```java
var name = switch ( item ) {
    case MenuItem.Action action -> action.name();
    case MenuItem.Menu subMenu -> subMenu.name();
};
```

This is a more concise and readable way to get the name of the menu item, compared to a traditional switch statement with `case` and `break` statements.

### Var Keyword

The `var` keyword, introduced in Java 10, allows you to declare a local variable without explicitly specifying its type. The compiler infers the type of the variable from the value that is assigned to it. This can make your code more concise and readable, especially when dealing with complex types.

In this project, `var` is used in several places, for example in the `Main.java` file:

```java
var counter = new Counter();
```

Here, the compiler infers that the type of the `counter` variable is `Counter`.