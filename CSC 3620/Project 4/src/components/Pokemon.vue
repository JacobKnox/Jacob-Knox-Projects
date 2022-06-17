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
                <div class="col-pull-right">
                    <button :class="'btn btn-' + pokemonPassed.type" @click="this.add">
                    Get
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: 'Pokemon',
  props: {
    pokemonPassed: Object
  },
  computed: {
      add() {
          this.$store.commit('addPokemon', this.pokemonPassed);
      }
  }
}
</script>
