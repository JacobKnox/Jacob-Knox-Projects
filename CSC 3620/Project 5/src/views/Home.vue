<template>
  <Header />
  <img src="../assets/gradphoto.png" alt="">
  <p>Yep... that's me. I bet you're wondering how I ended up on this frankly half-assed website. Well it's quite the story, really.
    My name is Jacob Knox and I'm currently a 20 year old college student. This photo was taken at my high school
    graduation and as you can likely tell, I was an accomplished graduate. Now I'm an exhausted, stressed second year
    throwing this website together almost entirely last minute and hoping to accomplish the bare minimum
    to salvage/maintain my grade.
  </p>
  <h2>Looking to the future..?</h2>
  <p>When I put it like that, the sights for my future, and the future of this website, may seem nonexistant
    or abysmal, but I have hope that everything will work out for me. It's just been a rough semester is all.
  </p>
  <h2>What is there to even do here?</h2>
  <p>Well you can go over to the About page and learn a little bit about my personality, education, and this
    sloppily put together website that I either intend on scrapping in favor of a much better built one or 
    improving on. You can also head over to Projects and check out a brief description of each project
    I've done for this class. That's about it. Enjoy it, or don't. Your call.
  </p>
  <Footer />
</template>

<script>
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'

export default {
  name: 'Home',
  components: {
    Header,
    Footer
  }
}
</script>
