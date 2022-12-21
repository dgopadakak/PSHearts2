package cardAndGame;

import suitAndDignity.Dignity;
import suitAndDignity.Suit;

public class Card
{
    private final Suit suit;
    private final Dignity dignity;

    public Card(Suit suit, Dignity dignity)
    {
        this.suit = suit;
        this.dignity = dignity;
    }

    public Suit getSuit()
    {
        return suit;
    }

    public Dignity getDignity()
    {
        return dignity;
    }

    public String getStringCard()
    {
        return suit.getTitle() + dignity.getTitle();
    }
}
