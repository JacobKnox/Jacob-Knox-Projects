<template>
  <div id="app">
      <div class="container">
        <nav class="navbar navbar-default">
          <div class="container-fluid">
            <div class="navbar-header">
                <router-link to="/" class="navbar-brand">Pokemon Collector</router-link>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li activeClass="active"><router-link to="/collection">Collection</router-link></li>
                    <li activeClass="active"><router-link to="/store">Pokemon Store</router-link></li>
                </ul>
                <strong class="navbar-text navbar-right">Pokemon: {{ this.size }}</strong>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown open">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Save & Load <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><router-link to="/">Save Data</router-link></li>
                            <li><a href="#">Load Data</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
        </nav>
      </div>
    </div>
</template>

<script>
// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyA_5oax2kK2dYQp3_hgQMqatWN1SRxWnCo",
  authDomain: "pokemon-collection-66c2c.firebaseapp.com",
  projectId: "pokemon-collection-66c2c",
  storageBucket: "pokemon-collection-66c2c.appspot.com",
  messagingSenderId: "95480542379",
  appId: "1:95480542379:web:c9e212b1c1ba0cdc7633ee"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
import { getFirestore, collection, addDoc } from "firebase/firestore";

export default {
  name: 'Header',
  computed: {
      size() {
        return this.$store.getters.collectionSize;
        },
    save: async () => {
          const db = getFirestore(app);
          const colRef = collection(db, "mycollection");
          const array = this.$store.getters.collection;
          array.forEach((pokemon) => {
              const docRef =  addDoc(colRef, {
                name: pokemon.name,
                level: pokemon.level,
                type: pokemon.type
            });
            console.log("Document written with ID: ", docRef.id);
          })
      }
  },
  methods: {
      
  }
}
</script>