// Declare the variables you want here
const squares = document.getElementsByClassName('square');
const modes = document.getElementsByClassName('mode');
const reset = document.querySelector('#reset');
const message = document.querySelector('#message');
const colorDisplay = document.querySelector('#colorDisplay');
const banner = document.querySelector('h1');
let rightSquare;
let red;
let green;
let blue;


// This calls the init function when the page first loads
init();

// Init is just anything you want to have happen when the page loads
function init(){
    // Add your setup code/functions here
    modes[0].addEventListener("click", easySetup);
    modes[1].addEventListener("click", hardSetup);
    resetText();
    for(i = 0; i < 6; i++){
      squares[i].addEventListener("click", check);
    }
    reset.addEventListener("click", init);
    if(modes[0].classList.contains('selected')){
      easySetup();
    }
    else{
      hardSetup();
    }
}


// I STRONGLY suggest you wrap all your remaining code in functions
// In fact you probably won't get the app working if you don't

function easySetup(){
  rightSquare = Math.floor(Math.random()*3);
  resetText();
  modes[0].classList.add('selected');
  modes[1].classList.remove('selected');
  for(i = 3; i < 6; i++){
    squares[i].style.visibility = 'hidden';
  }
  setColor(rightSquare);
}

function hardSetup(){
  rightSquare = Math.floor(Math.random()*6);
  resetText();
  modes[0].classList.remove('selected');
  modes[1].classList.add('selected');
  for(i = 3; i < 6; i++){
    squares[i].style.visibility = 'visible';
  }
  setColor(rightSquare);
}

function setColor(rightSquare){
  red = Math.floor(Math.random()*256);
  green = Math.floor(Math.random()*256);
  blue = Math.floor(Math.random()*256);
  for(i = 0; i < 6; i++){
    squares[i].style.backgroundColor = `rgb(${Math.floor(Math.random()*256)}, ${Math.floor(Math.random()*256)}, ${Math.floor(Math.random()*256)})`;
  }
  squares[rightSquare].style.backgroundColor = `rgb(${red}, ${green}, ${blue})`;
  colorDisplay.innerText = `RGB(${red}, ${green}, ${blue})`;
}

function check(){
  if(this.style.backgroundColor == `rgb(${red}, ${green}, ${blue})`){
    message.innerText = "Congratulations! You win!";
    reset.innerText = "Play Again?";
    banner.style.backgroundColor = `rgb(${red}, ${green}, ${blue})`;
    for(i = 0; i < 6; i++){
      squares[i].style.visibility = 'visible';
      squares[i].style.backgroundColor = `rgb(${red}, ${green}, ${blue})`;
    }
  }
  else{
    this.style.visibility = 'hidden';
    message.innerText = 'Oops! Try again!';
  }
}

function resetText(){
  message.innerText = '';
  reset.innerText = 'NEW COLORS';
  banner.style.backgroundColor = 'steelblue';
}
