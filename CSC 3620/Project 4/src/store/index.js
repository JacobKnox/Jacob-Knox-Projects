import { createStore } from 'vuex'

export default createStore({
  state: {
    collection: [],
    storePokemon: [
      {
        name: "Squirtle",
        level: 1,
        type: "info"
      },
      {
        name: "Bulbasaur",
        level: 1,
        type: "success"
      },
      {
        name: "Charmander",
        level: 1,
        type: "danger"
      },
      {
        name: "Pikachu",
        level: 1,
        type: "warning"
      }
    ]
  },
  getters: {
    storePokemon: (state) => {
      return state.storePokemon;
    },
    collection: (state) => {
      return state.collection;
    },
    collectionSize: (state) => {
      return state.collection.length;
    }
  },
  mutations: {
    addPokemon: (state, pokemon) => {
      state.collection.push(Object.assign({}, pokemon));
    },
    deletePokemon: (state, index) => {
      state.collection.splice(index, 1);
    },
    trainPokemon: (state, pokemon) => {
      pokemon.level++;
    }
  },
  actions: {
  },
  modules: {
  }
})
