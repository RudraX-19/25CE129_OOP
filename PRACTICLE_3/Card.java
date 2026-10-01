// ============================================================
//  PRACTICLE 3 - Part A - Task 2: Duplicate Card Check
//  File: PRACTICLE_3/Card.java
// ============================================================

import java.util.Objects;

class Card {
    private String rank;
    private String suit;
    
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }
    
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return rank.equals(card.rank) && suit.equals(card.suit);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}

public class DuplicateCardCheck {
    public static void main(String[] args) {
        System.out.println("=== Duplicate Card Checker ===\n");
        
        Card[] deck = new Card[10];
        int count = 0;
        
        // Add cards one by one
        Card[] cardsToAdd = {
            new Card("Ace", "Spades"),
            new Card("King", "Hearts"),
            new Card("Queen", "Diamonds"),
            new Card("Ace", "Spades"),  // duplicate
            new Card("Jack", "Clubs"),
            new Card("Queen", "Diamonds"), // duplicate
            new Card("Ten", "Hearts")
        };
        
        System.out.println("Adding cards to deck...\n");
        
        for (Card newCard : cardsToAdd) {
            // Check if card already exists
            boolean isDuplicate = false;
            for (int j = 0; j < count; j++) {
                if (newCard.equals(deck[j])) {
                    isDuplicate = true;
                    System.out.println("✗ Duplicate found: " + newCard);
                    break;
                }
            }
            
            if (!isDuplicate) {
                deck[count] = newCard;
                count++;
                System.out.println("✓ Added: " + newCard);
            }
        }
        
        System.out.println("\n=== Final Deck ===");
        System.out.println("Unique cards in deck: " + count);
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + deck[i]);
        }
    }
}
