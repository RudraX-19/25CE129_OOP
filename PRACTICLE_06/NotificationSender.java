// ============================================================
//  PRACTICLE 6 - Part A - Task 2: Notification Senders
//  Functional interface Notifier; email & SMS as lambdas;
//  marker interface Urgent — send twice for urgent senders
//  File: PRACTICLE_6/NotificationSender.java
// ============================================================

// Functional interface
@FunctionalInterface
interface Notifier {
    void send(String message);
}

// Marker interface — no methods
interface Urgent {}

// A holder so we can check instanceof Urgent at runtime
class NotifierHolder {
    private final Notifier notifier;
    private final String   label;

    public NotifierHolder(String label, Notifier notifier) {
        this.label    = label;
        this.notifier = notifier;
    }

    public Notifier getNotifier() { return notifier; }
    public String   getLabel()    { return label;    }
}

// An urgent notifier wrapper that also implements Urgent
class UrgentNotifierHolder extends NotifierHolder implements Urgent {
    public UrgentNotifierHolder(String label, Notifier notifier) {
        super(label, notifier);
    }
}

public class NotificationSender {
    public static void main(String[] args) {
        System.out.println("=== Notification Sender Demo ===\n");

        // Email sender lambda
        Notifier emailSender = message ->
            System.out.println("[EMAIL] Sending: " + message);

        // SMS sender lambda
        Notifier smsSender = message ->
            System.out.println("[SMS  ] Sending: " + message);

        // Push notification lambda — marked Urgent
        Notifier pushSender = message ->
            System.out.println("[PUSH ] Sending: " + message);

        // Hold them in an array — push is urgent
        NotifierHolder[] senders = {
            new NotifierHolder("Email", emailSender),
            new NotifierHolder("SMS",   smsSender),
            new UrgentNotifierHolder("Push", pushSender)  // Urgent
        };

        String message = "Your OTP is 482910. Valid for 5 minutes.";

        System.out.println("Broadcasting: \"" + message + "\"\n");

        for (NotifierHolder holder : senders) {
            holder.getNotifier().send(message);

            // Send twice for Urgent senders
            if (holder instanceof Urgent) {
                System.out.println("  [" + holder.getLabel() + " is URGENT — sending again]");
                holder.getNotifier().send(message);
            }
        }

        System.out.println("\n=== Broadcast complete ===");
    }
}
