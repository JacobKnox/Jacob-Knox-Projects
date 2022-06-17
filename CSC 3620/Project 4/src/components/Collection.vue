<template>
  <Header />
  <div class="row">
        <div class="col-xs-12">
            <div>
                <h1>My Pokemon Collection</h1>
                <br>
                <div v-if="this.$store.getters.collectionSize == 0">
                    <!-- Use this div if you don't have any -->
                    <p>You don't have any pokemon! Go buy some from the store.</p>
                </div>
                <div v-if="this.$store.getters.collectionSize > 0">
                    <!-- Else use this one and put Pokemon here -->
                    <div v-for="pokemon in this.$store.getters.collection" :key="pokemon">
                        <MyPokemon :pokemonPassed="pokemon" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Header from './Header.vue';
import MyPokemon from './MyPokemon.vue'

export default {
  name: 'Collection',
  components: {
    Header,
    MyPokemon
  }
}
</script>
