package cardAndGame;

import suitAndDignity.Dignity;
import suitAndDignity.Suit;

import java.util.ArrayList;
import java.util.List;

public class Game
{
    public static void startGame(int endScore)
    {
        ArrayList<Gamer> gamers = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            gamers.add(new Gamer());
        }
        int roundCounter = 0;
        while (gamers.get(0).getScore() < endScore && gamers.get(1).getScore() < endScore
                && gamers.get(2).getScore() < endScore && gamers.get(3).getScore() < endScore)
        {
            ArrayList<Card> deck = getDeck();       // Построение колоды
            for (Gamer gamer : gamers)      // Раздача игрокам
            {
                for (int j = 0; j < 13; j++)
                {
                    int id = (int) (Math.random() * deck.size());
                    gamer.addCard(deck.get(id));
                    deck.remove(id);
                }
            }

            if (roundCounter < 3)       // Нужен ли обмен карт
            {
                ArrayList<ArrayList<Card>> cardsForExchange = new ArrayList<>(new ArrayList<>());
                for (Gamer gamer : gamers)      // Отбиаем по 3 карты у каждого игрока
                {
                    cardsForExchange.add(gamer.getThreeRandomCardsAndDeleteThem());
                }
                switch (roundCounter)       // Обмен карт в зависимости от номера раунда
                {
                    case 0 -> {
                        for (int i = 0; i < 3; i++) {
                            gamers.get(0).addCard(cardsForExchange.get(3).get(i));
                            gamers.get(1).addCard(cardsForExchange.get(0).get(i));
                            gamers.get(2).addCard(cardsForExchange.get(1).get(i));
                            gamers.get(3).addCard(cardsForExchange.get(2).get(i));
                        }
                    }
                    case 1 -> {
                        for (int i = 0; i < 3; i++) {
                            gamers.get(0).addCard(cardsForExchange.get(1).get(i));
                            gamers.get(1).addCard(cardsForExchange.get(2).get(i));
                            gamers.get(2).addCard(cardsForExchange.get(3).get(i));
                            gamers.get(3).addCard(cardsForExchange.get(0).get(i));
                        }
                    }
                    case 2 -> {
                        for (int i = 0; i < 3; i++) {
                            gamers.get(0).addCard(cardsForExchange.get(2).get(i));
                            gamers.get(2).addCard(cardsForExchange.get(0).get(i));
                            gamers.get(1).addCard(cardsForExchange.get(3).get(i));
                            gamers.get(3).addCard(cardsForExchange.get(1).get(i));
                        }
                    }
                }
                roundCounter++;
            }
            else
            {
                roundCounter = 0;
            }


            boolean isFirstMoveInRound = true;
            boolean isHeartsBroken = false;
            int currMovePlayerID = -1;
            for (int i = 0; i < 13; i++)    // Цикл одного раунда
            {
                /*
                  Если это первый ход в раунде, то игрок, делающий первый ход определяется по наличию карты ♣2.
                  Последующие ходы начинает игрок, взявший взятку.
                 */
                if (isFirstMoveInRound)
                {
                    for (int j = 0; j < gamers.size(); j++)     // Поиск индекса игрока с двойкой треф
                    {
                        for (int k = 0; k < gamers.get(j).getNumOfCards(); k++)
                        {
                            if (gamers.get(j).getCards().get(k).getSuit() == Suit.CLUBS
                                    && gamers.get(j).getCards().get(k).getDignity() == Dignity.TWO)
                            {
                                currMovePlayerID = j;
                                break;
                            }
                        }
                    }
                }

                Card[] cardsOnTable = new Card[gamers.size()];
                Suit currentSuit = Suit.CLUBS;
                boolean isFirstMove = true;
                for (int j = 0; j < 4; j++)     // Цикл для одного круга
                {
                    int tempIndex = -1;
                    if (isFirstMoveInRound)     // Первый ход за раунд
                    {
                        for (Gamer gamer : gamers)      // Поиск индекса двойки треф
                        {
                            for (int o = 0; o < gamer.getNumOfCards(); o++)
                            {
                                if (gamer.getCards().get(o).getSuit() == Suit.CLUBS
                                        && gamer.getCards().get(o).getDignity() == Dignity.TWO) {
                                    tempIndex = o;
                                    break;
                                }
                            }
                        }
                        cardsOnTable[currMovePlayerID] = gamers.get(currMovePlayerID).getAndDeleteCard(tempIndex);
                        currentSuit = cardsOnTable[currMovePlayerID].getSuit();     // Установка основной масти
                        isFirstMoveInRound = false;
                        isFirstMove = false;
                    }
                    else
                    {
                        if (isFirstMove)        // Первый ход за круг
                        {
                            int tempIndexOfCard = (int) (Math.random() * gamers.get(currMovePlayerID).getNumOfCards());
                            if (!isHeartsBroken)        // Если червы еще не разбиты
                            {
                                while (gamers.get(currMovePlayerID).getCards().get(tempIndexOfCard).getSuit()
                                        == Suit.HEARTS)
                                {
                                    tempIndexOfCard = (int) (Math.random() * gamers
                                            .get(currMovePlayerID).getNumOfCards());
                                }
                            }
                            cardsOnTable[currMovePlayerID] = gamers.get(currMovePlayerID)
                                    .getAndDeleteCard(tempIndexOfCard);
                            currentSuit = cardsOnTable[currMovePlayerID].getSuit();     // Установка основной масти
                            isFirstMove = false;
                        }
                        else        // Обычный ход
                        {
                            boolean isHaveCurrSuit = false;
                            for (int k = 0; k < gamers.get(currMovePlayerID).getNumOfCards(); k++)
                            {
                                if (gamers.get(currMovePlayerID).getCards().get(k).getSuit() == currentSuit)
                                {
                                    isHaveCurrSuit = true;
                                    cardsOnTable[currMovePlayerID] = gamers.get(currMovePlayerID).getAndDeleteCard(k);
                                    break;
                                }
                            }
                            if (!isHaveCurrSuit)
                            {
                                int tempCardIndex = (int) (Math.random() * gamers.get(currMovePlayerID)
                                        .getNumOfCards());
                                cardsOnTable[currMovePlayerID] = gamers.get(currMovePlayerID)
                                        .getAndDeleteCard(tempCardIndex);
                                if (cardsOnTable[currMovePlayerID].getSuit() == Suit.HEARTS)
                                {
                                    isHeartsBroken = true;
                                }
                            }
                        }
                    }
                    if (currMovePlayerID < 3)       // Индекс следующего игрока
                    {
                        currMovePlayerID++;
                    }
                    else
                    {
                        currMovePlayerID = 0;
                    }
                }

                int tempScoreAddition = 0;      // Подсчет очков в конце круга
                for (int j = 0; j < 4; j++)
                {
                    if (cardsOnTable[j].getSuit() == Suit.HEARTS)
                    {
                        tempScoreAddition++;
                    }
                    if (cardsOnTable[j].getSuit() == Suit.SPADES && cardsOnTable[j].getDignity() == Dignity.QUEEN)
                    {
                        tempScoreAddition += 13;
                    }
                }
                int idOfLoser = getIdOfLoser(cardsOnTable, currentSuit);
                gamers.get(idOfLoser).addScore(tempScoreAddition);
                currMovePlayerID = idOfLoser;
            }
        }

        for (Gamer gamer : gamers)
        {
            System.out.println(gamer.getScore());
        }
    }

    private static ArrayList<Card> getDeck()
    {
        ArrayList<Card> deckForReturn = new ArrayList<>();
        for (int i = 0; i < Suit.values().length; i++)
        {
            for (int j = 0; j < Dignity.values().length; j++)
            {
                deckForReturn.add(new Card(Suit.values()[i], Dignity.values()[j]));
            }
        }
        return deckForReturn;
    }

    private static int getIdOfLoser(Card[] cardsOnTable, Suit currentSuit)
    {
        ArrayList<Dignity> gradationOfDignity = new ArrayList<>(List.of(Dignity.values()));
        int[] arrayOfDignities = new int[4];
        for (int i = 0; i < 4; i++)
        {
            if (cardsOnTable[i].getSuit() == currentSuit)
            {
                int dignity = -10;
                for (int j = 0; j < gradationOfDignity.size(); j++)
                {
                    if (cardsOnTable[i].getDignity() == gradationOfDignity.get(j))
                    {
                        dignity = j;
                        break;
                    }
                }
                arrayOfDignities[i] = dignity;
            }
            else
            {
                arrayOfDignities[i] = -1;
            }
        }
        int idOfMax = 0;
        int max = arrayOfDignities[idOfMax];
        for (int i = 0; i < 4; i++)
        {
            if (arrayOfDignities[i] > max)
            {
                max = arrayOfDignities[i];
                idOfMax = i;
            }
        }
        return idOfMax;
    }
}
