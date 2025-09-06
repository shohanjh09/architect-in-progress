/**
 * Liskov Substitution Principle (LSP)
 * If class B is a subtype of class A, then we should be able to replace A with B without breaking the behavior of the program.
 * Subclasses should extend the capabilities of the parent class not narrow it down.
 * In otherwords, any instance of a parent class should be able to be replaced with an instance of a child class without affecting the correctness of the program.
 *
 */


class Vehicle {
    public Integer getNumberOfWheels() {
        return 2;
    }

    public boolean hasEngine() {
        return true;
    }
}

clss Motorcycle extends Vehicle {

}

class Car extends Vehicle {
    @Override
    public Integer getNumberOfWheels() {
        return 4;
    }
}

class Bicycle extends Vehicle {
    @Override
    public boolean hasEngine() {
        return false; // Bicycles do not have engines
    }
}

public class Main {
    public static void main(String[] args) {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Motorcycle());
        vehicles.add(new Car());
        vehicles.add(new Bicycle());

        for (Vehicle vehicle : vehicles) {
            System.out.println("Has engine: " + vehicle.hasEngine().tostring());
            // give null pointer exception when calling hasEngine on Bicycle
        }
    }
}

/**
 * In this example, the Bicycle class violates the Liskov Substitution Principle because it changes the behavior of the hasEngine method.
 * If we replace a Vehicle instance with a Bicycle instance, any code that relies on the hasEngine method returning true will break or
 * we need change all the places manully where Bicycle is being used.
 * This means that the Bicycle class cannot be substituted for the Vehicle class without affecting the correctness of the program.
 */


/**
 * To adhere to the Liskov Substitution Principle, we can refactor the code as follows:
 */

class Vehicle {
    public Integer getNumberOfWheels() {
        return 2;
    }
}

class EngineVehicle extends Vehicle {
    public boolean hasEngine() {
        return true;
    }
}

class Motorcycle extends EngineVehicle {

}

class Car extends EngineVehicle {
    @Override
    public Integer getNumberOfWheels() {
        return 4;
    }
}

class Bicycle extends Vehicle {
}

public class Main {
    public static void main(String[] args) {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Motorcycle());
        vehicles.add(new Car());
        vehicles.add(new Bicycle());

        for (Vehicle vehicle : vehicles) {
            System.out.println("Has engine: " + vehicle.getNumberOfWheels().tostring());
            // No null pointer exception when calling hasEngine on Bicycle

            // Now if any vehicle is instance try to get hasEngine it will give compile time error
            System.out.println("Has engine: " + vehicle.hasEngine().tostring());

        }
    }
}

public class Main {
    public static void main(String[] args) {
        List<EngineVehicle> vehicles = new ArrayList<>();
        vehicles.add(new Motorcycle());
        vehicles.add(new Car());
        vehicles.add(new Bicycle()); // This will give compile time error

        for (Vehicle vehicle : vehicles) {
            System.out.println("Has engine: " + vehicle.hasEngine());
        }
    }
}

/**
 * In this refactored example, we have created a new class EngineVehicle that extends Vehicle and includes the hasEngine method.
 * The Motorcycle and Car classes extend EngineVehicle, while the Bicycle class extends Vehicle directly.
 * This way, the Bicycle class does not have the hasEngine method, and we cannot mistakenly call it on a Bicycle instance.
 * Now, any instance of Vehicle can be replaced with an instance of its subclasses without affecting the correctness of the program,
 * thus adhering to the Liskov Substitution Principle.
 */