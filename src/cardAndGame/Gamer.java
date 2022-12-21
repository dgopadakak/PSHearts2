package cardAndGame;

import java.util.ArrayList;

public class Gamer
{
    private int score = 0;
    private final ArrayList<Card> cards = new ArrayList<>();

    public void addCard(Card card)
    {
        cards.add(card);
    }

    public int getNumOfCards()
    {
        return cards.size();
    }

    public void addScore(int score)
    {
        this.score += score;
    }

    public int getScore()
    {
        return score;
    }

    public ArrayList<Card> getThreeRandomCardsAndDeleteThem()
    {
        ArrayList<Card> threeCards = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            int indexOfCard = (int) (Math.random() * cards.size());
            threeCards.add(cards.get(indexOfCard));
            cards.remove(indexOfCard);
        }
        return threeCards;
    }

    public ArrayList<Card> getCards()
    {
        return cards;
    }

    public Card getAndDeleteCard(int index)
    {
        Card cardForReturn = cards.get(index);
        cards.remove(index);
        return cardForReturn;
    }
}
