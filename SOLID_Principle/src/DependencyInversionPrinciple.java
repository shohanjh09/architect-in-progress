/**
 * Dependency Inversion Principle
 * Class should depend on interfaces rather than concrete classes.
 */

class WiredKeyboard {
    public WiredKeyboard() {
        System.out.println("Wired Keyboard connected");
    }
}

class WiredMouse {
    public WiredMouse() {
        System.out.println("Wired Mouse connected");
    }
}

class Mackbook {
    private final WiredKeyboard keyboard;
    private final WiredMouse mouse;

    public Mackbook() {
        this.keyboard = new WiredKeyboard();
        this.mouse = new WiredMouse();
    }
}

/**
 * In this example, the Mackbook class is tightly coupled with the WiredKeyboard and WiredMouse classes.
 * If we want to use a different type of keyboard or mouse, we need to modify the Mackbook class.
 * This violates the Dependency Inversion Principle.
 */

/**
 * Now let's refactor the code to adhere to the Dependency Inversion Principle
 */

interface Keyboard {

}

interface Mouse {

}

class Mackbook {
    private final Keyboard keyboard;
    private final Mouse mouse;

    public Mackbook(Keyboard keyboard, Mouse mouse) {
        this.keyboard = keyboard;
        this.mouse = mouse;
    }
}

/**
 * Now, the Mackbook class depends on the Keyboard and Mouse interfaces rather than the concrete classes.
 * So we can pass any implementation of the Keyboard and Mouse interfaces to the Mackbook class.
 * This adheres to the Dependency Inversion Principle.
 */