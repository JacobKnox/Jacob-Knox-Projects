// cards array holds all cards
let card = document.querySelectorAll(".card");
let cards = [...card];

// deck of all cards in game
const deck = document.querySelector(".deck");

// declaring variable of matchedCards
let matchedCard = [];

// array for opened cards
var openedCards = [];


// shuffle function
// @description shuffles cards
// @param {array}
// @returns shuffledarray
function shuffle(arr){
  for(let i = 0; i < arr.size; i++){
    let temp = arr[i];
    let newPos = Math.floor(Math.random(0, arr.length() - 1));
    arr[i] = arr[newPos];
    arr[newPos] = temp;
  }
  return arr;
}

// @description starts a new game when refreshed / loads
document.body.onload = startGame();

// startGame function
// @description function to start a new play
// @param {}
// @returns nothing
function startGame(){
  openedCards = [];
  matchedCards = [];
  cards = shuffle(cards);
  deck.innerHTML = "";
  Array.prototype.filter.call(cards, function(card){
      card.classList.remove('show');
      card.classList.remove('open');
      card.classList.remove('match');
      card.classList.remove('disable');
      deck.appendChild(card);
  });
}

// displayCard function
// @description toggles open, show, and disables classes to display cards
// @param {}
// @returns nothing
function displayCard(cardToDisplay){
  if(openedCards.length % 2 == 1){
    disable();
  }
  cardToDisplay.classList.add("show");
  cardToDisplay.classList.add("open");
  cardToDisplay.classList.add("disabled");
}

// cardOpen function
// @description add opened cards to OpenedCards list and check if cards are match or not
// @param {}
// @returns nothing
function cardOpen(cardToDisplay){
  openedCards[openedCards.length] = cardToDisplay;
  if(openedCards.length == 2){
    if(openedCards[0].type == openedCards[1].type){
      matched();
    }
    else{
      unmatched();
    }
  }
  setTimeout(() => enable(), 1500);
}


// matched function
// @description update cards when cards match
// @param {}
// @returns nothing
function matched(){
  Array.prototype.filter.call(openedCards, function(card){
    card.classList.add('match');
    card.classList.add('disabled');
    card.classList.remove("show");
    card.classList.remove("open");
    matchedCards[matchedCards.length] = matchedCard;
  });
  openedCards = [];
}


// unmatched function
// description update cards when cards don't match
// @param {}
// @returns nothing
function unmatched(){
  Array.prototype.filter.call(openedCards, function(card){
    card.classList.add('unmatch');
  });
  disable();
  setTimeout(() => {
    Array.prototype.filter.call(openedCards, function(card){
      card.classList.remove('show');
      card.classList.remove('open');
    });
    openedCards = [];
  }, 1100);
}

// disable function
// @description disable cards temporarily
// @param {}
// @returns nothing

function disable(){
    Array.prototype.filter.call(cards, function(card){
        card.classList.add('disabled');
    });
}


// enable function
// @description enable cards and disable matched cards
// @param {}
// @returns nothing

function enable(){
    Array.prototype.filter.call(cards, function(card){
        card.classList.remove('disabled');
    });
    for(var i = 0; i < matchedCard.length; i++){
        matchedCard[i].classList.add("disabled");
    }
}


// loop to add event listeners to each card to call displayCard and cardOpen functions
setTimeout(() => {
  for(var i = 0; i < cards.length; i++){
    cards[i].addEventListener("click", function(){
      displayCard(this);
      cardOpen(this);
    });
  }
}, 1000);
