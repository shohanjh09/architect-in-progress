/**
 * Factory Method Design Pattern:
 * 
 */



Soutions:
public interface Burger {

    void prepare();

}

public class ClassicBurger implements Burger {

    @Override
    public void prepare() {
        // Prepare Classic Burger
        System.out.println("Preparing Classic Burger...");
    }

}

public class OrientalBurger implements Burger {

    @Override
    public void prepare() {
        // Prepare Oriental Burger
        System.out.println("Preparing Oriental Burger...");
    }

}

public class ClassicRestaurant extends Restaurant {

    @Override
    protected Burger createBurger() {
        System.out.println("Creating Classic Burger...");
        return new ClassicBurger();
    }

}

public class OrientalRestaurant extends Restaurant {

    @Override
    protected Burger createBurger() {
        System.out.println("Creating Oriental Burger...");
        return new OrientalBurger();
    }

}

public abstract class Restaurant {

    public void orderBurger() {
        System.out.println("Ordering Burger...");
        Burger burger = createBurger();
        burger.prepare();
    }

    protected abstract Burger createBurger();

}