--------
Wallapop Challenge: War of Suites.
--------

--------
Summary:
--------
War of Suites Game
This project was created using Android Studio Dolphin | 2021.3.1

--------
Details:
--------

I wanted to keep this game as simple as possible using just one ViewModel to keep the game aware of the Activity livecycle and keep scores and decks values even if player
sents the app to background.

As you guys will see in the code, I kept a simple logic:

* One main deck (mutable list of Cards):  which is shuffled at the beggining of every game before splitting it in two decks
* Two decks (mutableLists of Cards) (one for each player) to Keep scores and deal cards
* To allow to establish the Suite values in case of draw I created two lists (one for suite names and other for values (1-4)) and randomly assigned a value to each suite name in a Map
* The UI consists of 2 ImageViews where the cards are dealt, 2 ImageViews with counters to simulate the players decks and two ImageViews with counters acting as discard piles. 
* The scores for the players, cards on discard piles and players deck remaining cards are visible and updated at all times
* The ViewModel is responsible for pretty much every operation on the game in order to keep the scores up-to-date

-------------------
A little sneak peek:
-------------------


![Animation](https://user-images.githubusercontent.com/42120921/192663675-680851cc-f96f-4933-a946-eda9fe86677b.gif)

For more details on the purpose of this feature, its limitations and detailed usage,
please read the SDK guide at
http://developer.android.com
