// Change card subtitles based on current weather
const cardSubtitles = document.querySelectorAll(".card-subtitle");
// Change icon using .innerHTML element on the card texts
const cardText = document.querySelectorAll(".card-text");
// Use card links to figure out which one is being forecast
const cardLinks = document.querySelectorAll(".card-link");
const cards = document.querySelectorAll(".card");
// Change modal title based on link clicked
const modalTitle = document.querySelector("#modal-title");
// Can use .innerHTML to edit the icon and text of the modal items
const modalItems = document.querySelectorAll(".list-group-item");
let lakeland = new XMLHttpRequest();
let oldTown = new XMLHttpRequest();
let jacksonville = new XMLHttpRequest();
let chicago = new XMLHttpRequest();

function init(){
  for(i = 0; i < 4; i++){
    cards[i].style.visibility = "hidden";
  }
  lakeland.open("GET", "https://api.openweathermap.org/data/2.5/onecall?lat=28.0395&lon=-81.9498&appid=7a04622a402357675ec4a0c4e0c1392b", true);
  oldTown.open("GET", "https://api.openweathermap.org/data/2.5/onecall?lat=29.6017&lon=-82.9819&appid=7a04622a402357675ec4a0c4e0c1392b", true);
  jacksonville.open("GET", "https://api.openweathermap.org/data/2.5/onecall?lat=30.3322&lon=-81.6557&appid=7a04622a402357675ec4a0c4e0c1392b", true);
  chicago.open("GET", "https://api.openweathermap.org/data/2.5/onecall?lat=41.8781&lon=-87.6298&appid=7a04622a402357675ec4a0c4e0c1392b", true);
  lakeland.send();
  oldTown.send();
  jacksonville.send();
  chicago.send();
}

function getIcon(code){
  if(code > 199 && code < 233){
    return "cloud-lightning";
  }
  else if(code > 299 && code < 322){
    return "cloud-rain";
  }
  else if(code > 499 && code < 532){
    return "cloud-rain-heavy";
  }
  else if(code > 599 && code < 623){
    return "cloud-snow";
  }
  else if(code > 699 && code < 772){
    return "cloud-fog";
  }
  else if(code == 781){
    return "tornado";
  }
  else if(code == 800){
    return "sun";
  }
  else if(code > 800 && code < 805){
    return "cloudy";
  }
  else{
    return "patch-question";
  }
}

init();

lakeland.onload = function(){
  if(lakeland.status == 200){
    lakeland = JSON.parse(lakeland.response);
    cardSubtitles[0].innerText = `${lakeland.current.weather[0]["main"]} - ${Math.floor((lakeland.current.temp - 273.15)*(9/5) + 32)}\u00B0F`;
    cards[0].style.visibility = "visible";
    cardText[0].innerHTML = `<i class='bi bi-${getIcon(lakeland.current.weather[0]["id"])}'></i>`;
  }
};

oldTown.onload = function(){
  if(oldTown.status == 200){
    oldTown = JSON.parse(oldTown.response);
    cardSubtitles[1].innerText = `${oldTown.current.weather[0]["main"]} - ${Math.floor((oldTown.current.temp - 273.15)*(9/5) + 32)}\u00B0F`;
    cards[1].style.visibility = "visible";
    cardText[1].innerHTML = `<i class='bi bi-${getIcon(oldTown.current.weather[0]["id"])}'></i>`;
  }
};

jacksonville.onload = function(){
  if(jacksonville.status == 200){
    jacksonville = JSON.parse(jacksonville.response);
    cardSubtitles[2].innerText = `${jacksonville.current.weather[0]["main"]} - ${Math.floor((jacksonville.current.temp - 273.15)*(9/5) + 32)}\u00B0F`;
    cards[2].style.visibility = "visible";
    cardText[2].innerHTML = `<i class='bi bi-${getIcon(jacksonville.current.weather[0]["id"])}'></i>`;
  }
};

chicago.onload = function(){
  if(chicago.status == 200){
    chicago = JSON.parse(chicago.response);
    cardSubtitles[3].innerText = `${chicago.current.weather[0]["main"]} - ${Math.floor((chicago.current.temp - 273.15)*(9/5) + 32)}\u00B0F`;
    cards[3].style.visibility = "visible";
    cardText[3].innerHTML = `<i class='bi bi-${getIcon(chicago.current.weather[0]["id"])}'></i>`;
  }
};

cardLinks[0].addEventListener("click", function(){
  modalTitle.innerText = "Forecast for Lakeland";
  for(i = 0; i < 7; i++){
    modalItems[i].innerHTML = `<i class='bi bi-${getIcon(lakeland.daily[i].weather[0]["id"])} me-3'></i>${Math.floor((lakeland.daily[i].temp.max - 273.15)*(9/5) + 32)}\u00B0F / ${Math.floor((lakeland.daily[i].temp.min - 273.15)*(9/5) + 32)}\u00B0F - ${lakeland.daily[i].weather[0]["main"]}, ${lakeland.daily[i].wind_speed} mph wind`;
  }
});

cardLinks[1].addEventListener("click", function(){
  modalTitle.innerText = "Forecast for Old Town";
  for(i = 0; i < 7; i++){
    modalItems[i].innerHTML = `<i class='bi bi-${getIcon(oldTown.daily[i].weather[0]["id"])} me-3'></i>${Math.floor((oldTown.daily[i].temp.max - 273.15)*(9/5) + 32)}\u00B0F / ${Math.floor((oldTown.daily[i].temp.min - 273.15)*(9/5) + 32)}\u00B0F - ${oldTown.daily[i].weather[0]["main"]}, ${oldTown.daily[i].wind_speed} mph wind`;
  }
});

cardLinks[2].addEventListener("click", function(){
  modalTitle.innerText = "Forecast for Jacksonville";
  for(i = 0; i < 7; i++){
    modalItems[i].innerHTML = `<i class='bi bi-${getIcon(jacksonville.daily[i].weather[0]["id"])} me-3'></i>${Math.floor((jacksonville.daily[i].temp.max - 273.15)*(9/5) + 32)}\u00B0F / ${Math.floor((jacksonville.daily[i].temp.min - 273.15)*(9/5) + 32)}\u00B0F - ${jacksonville.daily[i].weather[0]["main"]}, ${jacksonville.daily[i].wind_speed} mph wind`;
  }
});

cardLinks[3].addEventListener("click", function(){
  modalTitle.innerText = "Forecast for Chicago";
  for(i = 0; i < 7; i++){
    modalItems[i].innerHTML = `<i class='bi bi-${getIcon(chicago.daily[i].weather[0]["id"])} me-3'></i>${Math.floor((chicago.daily[i].temp.max - 273.15)*(9/5) + 32)}\u00B0F / ${Math.floor((chicago.daily[i].temp.min - 273.15)*(9/5) + 32)}\u00B0F - ${chicago.daily[i].weather[0]["main"]}, ${chicago.daily[i].wind_speed} mph wind`;
  }
});
