<template>
  <Header />
  <h2>Pokemon Collection</h2>
  <img src="../assets/pokemoncollector.png" alt="">
  <p>A website built using VueJS that allows for simple collection, training, and release of 4 different Pokemon.</p>
  <h2>Monster Game</h2>
  <img src="../assets/monstergame.png" alt="">
  <p>A website built using VueJS that is a basic monster fighting game. Hosted <a href="https://monstgame.firebaseapp.com/">here</a> using Google's FireBase hosting.</p>
  <h2>Quotes Board</h2>
  <img src="../assets/quotesboard.png" alt="">
  <p>A basic VueJS website that allows the user to add up to 10 quotes to a "quote board" and delete them.</p>
  <h2>Matching Game</h2>
  <img src="../assets/matchinggame.png" alt="">
  <p>A basic vanilla JavaScript-powered site that is a matching game.</p>
  <Footer />
</template>

<script>
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'

export default {
  name: 'Projects',
  components: {
    Header,
    Footer
  }
}
</script>
