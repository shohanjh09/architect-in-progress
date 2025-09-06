/**
 * Singleton Design Pattern:
 * Only one instance of its kind exists and provide a single point of acess to it.
 * Let you access your object from anywhere in your application.
 * Guarantees that only one instance of the class will be available at any point of time.
 */

public class Singleton {
    private static volatile Singleton instance; // volatile keyword ensures that multiple threads handle the instance variable correctly when it is being initialized to the Singleton instance.
    private String data;

    // make sure it is private so that it cannot be instantiated outside the class
    private Singleton(String data){
        this.data = data;
    }

    public static Singleton getInstance(String data){
        // nothing in this prevents two threads from acceessing this piece of code at the same time
//        if(instance == null){
//            instance = new Singleton(data);
//        }

        // limiting synchronization to the rare case of constructing a new instace of this field.
        Singleton result = instance;
        if(result == null){
            synchronized (Singleton.class){
                result = instance;
                if(result == null){
                    instance = result = new Singleton(data);
                }
            }
        }
        return instance;
    }
}

/**
 * So singleton should be used when a class muct have a single instance available.
 * Diasables all means of creating objects of a class except  for the specific static creation method.
 * Returns the existing instnace if it has already been created.
 * its code needs to be adapted to handle multi threads.
 */