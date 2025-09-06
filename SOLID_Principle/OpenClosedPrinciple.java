/**
 * Open/Closed Principle
 * A class should be open for extension but closed for modification.
 */

class InvoieDao {
    private Invoice invoice;

    public InvoieDao(Invoice invoice) {
        this.invoice = invoice;
    }

    public void saveToDB(Invoice invoice) {
        // Code to save invoice to database
    }
}

/**
 * Assume my code is well tested and now in the live and working fine. Now I will add a new feature to save the invoice to a file like the following code example.
 */


class InvoieDao {
    private Invoice invoice;

    public InvoieDao(Invoice invoice) {
        this.invoice = invoice;
    }

    public void saveToDB(Invoice invoice) {
        // Code to save invoice to database
    }

    public void saveToFile(Invoice invoice) {
        // Code to save invoice to file
    }
}

/**
 * In this example, we broke the Open/Closed Principle because we had to modify the InvoieDao class to add the new saveToFile method.
 */

/**
 * Now let's refactor the code to adhere to the Open/Closed Principle
 */

interface InvoicePersistence {
    void save(Invoice invoice);
}

class InvoiceDBPersistence implements InvoicePersistence {
    public void save(Invoice invoice) {
        // Code to save invoice to database
    }
}

class InvoiceFilePersistence implements InvoicePersistence {
    public void save(Invoice invoice) {
        // Code to save invoice to file
    }
}

/**
 * Now, if we need to add a new way to save the invoice, we can simply create a new class that implements the InvoicePersistence interface.
 * We don't need to modify any existing code, so the code is closed for modification but open for extension.
 */