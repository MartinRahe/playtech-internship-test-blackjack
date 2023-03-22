# playtech-internship-test-blackjack

## I deduced the following list of ways a turn can be faulty:
- [x] a card appears twice
- [x] action after player bust is anything but "P Lose"
- [x] after revealing, dealer stops hitting before reaching 17
- [x] after revealing, dealer continues hitting after reaching 17
- [x] player hits after standing
- [x] dealer reveals before player stands
- [x] player loses if dealer busts
- [x] lower hand total wins
- [x] dealer wins on equal hands
- [x] same session is played again after player leaves
- [x] player joins a session twice
- [x] dealer starts with both cards hidden, both cards revealed, or a number of cards differing from two
- [x] player does not start with two face up cards
- [x] a hit does not affect the correct card count
- [x] some cards are invalid

## Operation
The program starts by reading in lines from the input file, splitting them by the ',' character.
Lines that don't split into exactly 6 elements are thrown away as garbage. Lines are then sorted by game id and timestamp.

Once lines have been extracted, a "simulator" steps through the turns. An empty list of invalid turns is initiated.
If a faulty turn was previously seen with the same game id, the turn is skipped over.

The simulator starts each turn with a set of valid actions for this turn, which depends on the action of the previous turn.
We assume that no turn is missing from the middle of a game, although the end of a game could be missing.

It is assumed that if an action is invalid, then the card data of this turn is garbage and the turn is added to the list of invalid turns.

If the game id is new, the only valid action is joining a game.

Next we check if the player has gone bust, in that case the only valid action other than leaving or starting a new game is the player losing.
Similarly if the dealer has gone bust then the only valid action other than leaving or starting a new game is the player winning.

If it is concluded that the current action could be valid, we look at the cards on the table.
Since each turn can either change the cards visible on the table or has a possible previous turn that does so, we have to evaluate the cards each turn.
The simulator checks that all cards are valid cards from a regular 52 card deck, no card appears twice, and the correct number of cards (both visible and total), is present for the dealer and the player.
If a winner is supposed to be concluded, we also check that the win conditions for the sums of cards are satisfied.