<template>
    <Header />
    <div class="row">
        <div class="col-xs-12">
            <div>
                <h1>Perfectly-Proportioned Premium Pokemon Proprietor</h1>
                <br>
                <div v-for="pokemon in storePokemon" :key="pokemon">
                    <Pokemon :pokemonPassed="pokemon" />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Header from './Header.vue';
import Pokemon from './Pokemon.vue';
import { mapState } from 'vuex'


export default {
  name: 'Store',
  components: {
    Header,
    Pokemon
  },
  computed: mapState({
      storePokemon: 'storePokemon'
      })
}
</script>
