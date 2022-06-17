<template>
  <Header />
  <div id="app">
      <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <div>
                    <h1>Gotta Catch Em All</h1>
                    <h6>You may Save & Load your Data</h6>
                    <h6>Click Collection to see your Pokemon</h6>
                    <h6>Click Pokemon to get more Pokemon</h6>
                    <hr>
                    <p>Total Pokemon Collected: {{ this.size }}</p>
                </div>
            </div>
        </div>
      </div>
    </div>
</template>

<script>
import Header from './Header.vue';

export default {
  name: 'Home',
  components: {
    Header
  },
  computed: {
    size() {
      return this.$store.getters.collectionSize
    }
  }
}
</script>
