<template>
    <section class="row">
        <div class="small-6 columns">
            <h1 class="text-center">YOU</h1>
            <div class="healthbar">
                <div class="healthbar text-center" :style="{backgroundColor: 'green', margin: 0, color: 'white', width: humanHP + '%'}">
                  {{ humanHP }}
                </div>
            </div>
        </div>
        <div class="small-6 columns">
            <h1 class="text-center">MONSTER</h1>
            <div class="healthbar">
                <div class="healthbar text-center" :style="{backgroundColor: 'green', margin: 0, color: 'white', width: monsterHP + '%'}">
                  {{ monsterHP }}
                </div>
            </div>
        </div>
    </section>
    <section id="startSection" class="row controls">
        <div class="small-12 columns">
            <button id="start-game" v-on:click="start">START NEW GAME</button>
        </div>
    </section>
    <section id="controlsSection" class="row controls" style="visibility:hidden;">
        <div class="small-12 columns">
          <button id="attack" v-on:click="attack">ATTACK</button>
          <button id="special-attack" v-on:click="specialAttack">SPECIAL ATTACK</button>
          <button id="heal" v-on:click="heal">HEAL</button>
          <button id="heal" v-on:click="healMonster">HEAL ENEMY?</button>
          <button id="give-up" v-on:click="giveUp">GIVE UP</button>
      </div>
    </section>
    <section class="row log">
        <div class="small-12 columns">
            <ul id="log">
                <li>

                </li>
            </ul>
        </div>
    </section>
</template>

<script>

export default {
  name: 'App',
  data: () => {
    return{
      humanHP: 100,
      monsterHP: 100
    }
  },
  components: {

  },
  methods: {
    start : function(){
      document.getElementById("startSection").style.visibility = "hidden";
      document.getElementById("controlsSection").style.visibility = "visible";
      this.humanHP = 100;
      this.monsterHP = 100;
      document.getElementById("log").innerHTML = "";
    },
    heal : function(){
      if(this.humanHP < 90){
        document.getElementById("log").innerHTML = "<li style='background-color: lightblue; color: blue'>Player Heals for 10</li>" + document.getElementById("log").innerHTML;
        this.humanHP = this.humanHP + 10;
      }
      else{
        document.getElementById("log").innerHTML = "<li style='background-color: lightblue; color: blue'>Player Heals for " + (100 - this.humanHP) + "</li>" + document.getElementById("log").innerHTML;
        this.humanHP = 100;
      }
      this.monsterAttack();
    },
    healMonster : function(){
      if(this.monsterHP < 90){
        document.getElementById("log").innerHTML = "<li style='background-color: rgb(255, 140, 140); color: red'>Monster Heals for 10</li>" + document.getElementById("log").innerHTML;
        this.monsterHP = this.monsterHP + 10;
      }
      else{
        document.getElementById("log").innerHTML = "<li style='background-color: rgb(255, 140, 140); color: red'>Monster Heals for " + (100 - this.monsterHP) + "</li>" + document.getElementById("log").innerHTML;
        this.monsterHP = 100;
      }
      this.monsterAttack();
    },
    attack : function(){
      var damage = Math.floor(Math.random() * (10 - 3) + 3);
      this.monsterHP = this.monsterHP - damage;
      document.getElementById("log").innerHTML = "<li style='background-color: lightblue; color: blue'>Player Hits Monster for " + damage + "</li>" + document.getElementById("log").innerHTML;
      if(this.monsterHP <= 0){
        this.victory();
      }
      else{
        this.monsterAttack();
      } 
    },
    victory : function(){
      if(confirm("Congratulations! You've won! Would you like to play again?")){
        this.start();
      }
      else{
        document.getElementById("startSection").style.visibility = "visible";
        document.getElementById("controlsSection").style.visibility = "hidden";
      }
    },
    giveUp : function(){
      document.getElementById("startSection").style.visibility = "visible";
      document.getElementById("controlsSection").style.visibility = "hidden";
    },
    specialAttack : function(){
      var damage = Math.floor(Math.random() * (20 - 10) + 10);
      this.monsterHP = this.monsterHP - damage;
      document.getElementById("log").innerHTML = "<li style='background-color: lightblue; color: blue'>Player Hits Monster Hard for " + damage + "</li>" + document.getElementById("log").innerHTML;
      if(this.monsterHP <= 0){
        this.victory();
      }
      else{
        this.monsterAttack();
      }
    },
    monsterAttack : function(){
      var damage = Math.floor(Math.random() * (12 - 5) + 5)
      this.humanHP = this.humanHP - damage;
      document.getElementById("log").innerHTML = "<li style='background-color: rgb(255, 140, 140); color: red'>Monster Hits Player for " + damage + "</li>" + document.getElementById("log").innerHTML;
      if(this.humanHP <= 0){
        this.loss();
      }
    },
    loss : function(){
      if(confirm("Oops! You've succumed to your injuries. Would you like to play again?")){
        this.start();
      }
      else{
        document.getElementById("startSection").style.visibility = "visible";
        document.getElementById("controlsSection").style.visibility = "hidden";
      }
    }
  }
}

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

</style>
