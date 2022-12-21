package suitAndDignity;

public enum Suit        // https://javarush.com/groups/posts/1963-kak-ispoljhzovatjh-klass-enum
{
    SPADES ("♠"),
    CLUBS ("♣"),
    HEARTS ("♥"),
    DIAMONDS("♦");

    private final String title;

    Suit(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    @Override
    public String toString()
    {
        return "Suit{title='" + title + "'}";
    }
}
