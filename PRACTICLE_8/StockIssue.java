// ============================================================
//  PRACTICLE 8 - Part A - Task 2: Stock Issue
//  Warehouse.issue() throws checked OutOfStockException (with
//  shortfall) and InvalidQuantityException for qty <= 0
//  File: PRACTICLE_8/StockIssue.java
// ============================================================

// ---- Custom exceptions ----

class WarehouseException extends Exception {
    public WarehouseException(String message) { super(message); }
}

class OutOfStockException extends WarehouseException {
    private final int shortfall;

    public OutOfStockException(String item, int requested, int available) {
        super("'" + item + "': requested " + requested
            + ", only " + available + " in stock");
        this.shortfall = requested - available;
    }

    public int getShortfall() { return shortfall; }
}

class InvalidQuantityException extends WarehouseException {
    public InvalidQuantityException(int qty) {
        super("Quantity must be > 0, got: " + qty);
    }
}

// ---- Warehouse ----

class Warehouse {
    private String[] items;
    private int[]    stock;

    public Warehouse(String[] items, int[] stock) {
        this.items = items;
        this.stock = stock;
    }

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) throw new InvalidQuantityException(qty);

        for (int i = 0; i < items.length; i++) {
            if (items[i].equalsIgnoreCase(item)) {
                if (qty > stock[i]) {
                    throw new OutOfStockException(item, qty, stock[i]);
                }
                stock[i] -= qty;
                System.out.println("Issued " + qty + " x " + item
                                 + "  (remaining: " + stock[i] + ")");
                return;
            }
        }
        throw new OutOfStockException(item, qty, 0);
    }

    public void printStock() {
        System.out.println("--- Current Stock ---");
        for (int i = 0; i < items.length; i++) {
            System.out.printf("  %-15s : %d%n", items[i], stock[i]);
        }
    }
}

// ---- Driver ----

public class StockIssue {
    public static void main(String[] args) {
        System.out.println("=== Warehouse Stock Issue ===\n");

        String[] items = { "Widget", "Gadget", "Sprocket", "Doohickey" };
        int[]    stock = {     50,       10,        5,           0      };

        Warehouse wh = new Warehouse(items, stock);
        wh.printStock();

        // Batch of requests — some will fail
        System.out.println();
        String[][] requests = {
            { "Widget",    "20"  },
            { "Gadget",    "15"  },   // out of stock (shortfall 5)
            { "Sprocket",  "-3"  },   // invalid qty
            { "Doohickey", "1"   },   // out of stock
            { "Widget",    "25"  },
            { "Sprocket",  "3"   }
        };

        for (String[] req : requests) {
            String item = req[0];
            int    qty  = Integer.parseInt(req[1]);
            System.out.printf("Request: %d x %s  -->  ", qty, item);

            try {
                wh.issue(item, qty);
            } catch (InvalidQuantityException e) {
                System.out.println("INVALID: " + e.getMessage());
            } catch (OutOfStockException e) {
                System.out.println("OUT OF STOCK: " + e.getMessage()
                                 + "  (shortfall: " + e.getShortfall() + ")");
            }
        }

        System.out.println();
        wh.printStock();
    }
}
