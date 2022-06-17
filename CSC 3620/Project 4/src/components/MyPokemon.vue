<template>
    <div class="col-sm-6 col-md-4">
        <div :class="'panel panel-' + pokemonPassed.type">
            <div class="panel-heading">
                <h3 class="panel-title">
                {{ pokemonPassed.name }}
                <small>(Level: {{ pokemonPassed.level }})</small>
                </h3>
            </div>
            <div class="panel-body">
                <div class="pull-right">
                    <button 
                    :class="'btn btn-' + pokemonPassed.type" @click="pokemonPassed.level++">
                        Train
                    </button>
                    <button 
                        :class="'btn btn-' + pokemonPassed.type" @click="this.delete">
                        Release
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: 'MyPokemon',
  props: {
    pokemonPassed: Object
  },
  computed: {
    delete() {
      this.$store.commit('deletePokemon', this.$store.getters.collection.indexOf(this.pokemonPassed));
    },
    levelUp() {
        this.$store.commit('trainPokemon', this.pokemonPassed);
    }
  }
}
</script>
