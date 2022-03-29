<template>
  <h1>Quotes Added</h1>
  <div id="bar">
    <div id="bar-fill">
      <p>{{ numQuotes }} / 10</p>
    </div>
  </div>
  <form id="form" @submit.prevent>
    <p id="label">Quote</p>
    <textarea name="text" id="quoteArea" cols="100" rows="5"></textarea><br>
    <button id="submit" @click="newQuote">
      Add Quote
    </button>
  </form>
  <div id="quotes">
    <div v-for="quote in quotes" :key="quote" class="quote" :id="quotes.indexOf(quote)" @click="delQuote">
      {{ quote }}
    </div>
  </div>
  <div id="infoBox">
    <p>Info: Click a quote to delete it.</p>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return{
      quotes: [],
      numQuotes: 0
    }
  },
  methods: {
    delQuote(element){
      console.log(element);
      const id = parseInt(element.target.id);
      this.quotes.splice(id, 1);
      this.numQuotes--;
      document.getElementById("bar-fill").style.width = this.numQuotes * 10 + "%";
    },
    newQuote(){
      const quote = document.getElementById("quoteArea");
      console.log(quote.value);
      if(this.numQuotes > 9){
        alert("You've reached your quote limit. Try deleting a quote before adding a new one.")
      }
      else if(quote.value == " " || quote.value == ""){
        alert("You've attempted to submit an empty quote. Please enter something in the text box before pressing 'Add Quote.'")
      }
      else{
        this.quotes.push(quote.value);
        this.numQuotes++;
        document.getElementById("bar-fill").style.width = this.numQuotes * 10 + "%";
        quote.value = " ";
        this.$nextTick(() => {
          if(this.numQuotes == 4 || this.numQuotes == 8){
            const change = document.getElementById(`${this.numQuotes - 1}`);
            change.style.marginRight = 0;
          }
        });
      }
    }
  }
}
</script>

<style>
body{
  margin: 20px 20px;
}
form p{
  margin-bottom: 10px;
}
form{
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 20px;
  margin-top: 20px;
  width: -webkit-fit-content;
  width: -moz-fit-content;
  width: fit-content;
  text-align: left;
}
#quoteArea{
  margin: auto;
  border-radius: 4px;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
h1{
  text-align: left;
  margin: 0;
}
#bar{
  background-color: #D3D3D3;
  width: 100%;
  border-radius: 10px;
}
#bar-fill{
  background-color: #1167B1;
  width: 0%;
  color: white;
  border-radius: 10px;
  transition: width 1s;
}
#bar-fill p{
  min-width: 50px;
}
#submit{
  margin-top: 10px;
  color: white;
  border-radius: 4px;
  background-color: #1167B1;
  border-color: #1167B1;
  width: 80px;
  height: 30px;
}
#submit:hover{
  cursor: pointer;
}
#quotes{
  display: flex;
  align-content: flex-start;
  flex-wrap: wrap;
}
.quote{
  font-style: oblique;
  font-family: Times, Times New Roman, serif;
  width: 19.1%;
  margin-right: 5%;
  margin-bottom: 20px;
  align-self: flex-start;
  border-style: solid;
  border-color: lightgray;
  border-width: 1px;
  border-radius: 8px;
  padding: 1% 1%;
  overflow-wrap: break-word;
  text-align: left;
}
.quote:hover{
  background-color: #FFA3A6;
  cursor: pointer;
}
#infoBox{
  background-color: #BCD2E8;
  border-radius: 8px;
  height: 50px;
  display: flex;
}
#infoBox p{
  margin: auto;
  color: #1338BE;
}
</style>
