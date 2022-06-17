<template>
  <Header />
  <div id="aboutMe">
    <h1>Who I Am</h1>
    <div id="personal">
      <p>I consider myself a young intellectual with an eccentric personality. As profound as some of my thoughts may seem to myself, I always try to humble myself and acknowledge that at heart part of me will always be one big child.</p>
    </div>
    <h1>Education</h1>
    <div id="education">
      <div id="fsc" class="card bg-danger text-white mx-1 my-2">
        <h2 class="text-center mt-4">Florida Southern College</h2>
        <div id="fsc-body" class="card-body">
          <p>Bachelor's of Science</p>
          <p>Majors: Computer Science, Mathematics</p>
          <p>Minors: Ethics, Data Analytics</p>
          <p>I am currently pursuing an undergraduate education at Florida Southern College in Lakeland, FL
            with an expected graduation in May 2024. I am studying computer science, with concentrations in 
            web and cloud computing and artificial intelligence, and mathematics.
          </p>
        </div>
      </div>
      <div id="fgc" class="card bg-success text-white mx-1 my-2">
        <h2 class="text-center mt-4">Florida Gateway College</h2>
        <p class="card-body">I took dual enrollment courses at Florida Gateway College throughout my high school career.</p>
      </div>
      <div id="erau" class="card bg-primary text-white mx-1 my-2">
        <h2 class="text-center mt-4">Embry-Riddle Aeronautical University</h2>
        <p class="card-body">I took dual enrollment courses at Embry-Riddle Aeronautical University throughout my high school career.</p>
      </div>
    </div>
  </div>
  <div id="aboutWebsite">
    <h1>This Website</h1>
    <p>This website is my final project for the second course in Florida Southern College's web development concentration.</p>
    <h2>Styles</h2>
    <p>The stylistic choices are, at the moment, a random mess frankly since I'm not a very creative person. The background is a sky blue, because I figured it was calming and blue is my favorite color. Each of the schools above have a background corresponding to their most prominent color.</p>
  </div>
  <Footer />
</template>

<script>
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'

export default {
  name: 'About',
  components: {
    Header,
    Footer
  }
}
</script>

<style scoped>
  
</style>