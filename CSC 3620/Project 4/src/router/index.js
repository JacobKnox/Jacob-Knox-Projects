import { createRouter, createWebHashHistory } from 'vue-router'
import Home from '../components/Home.vue'
import Collection from '../components/Collection.vue'
import Store from '../components/Store.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/collection',
    name: 'Collection',
    component: Collection
  },
  {
    path: '/store',
    name: 'Store',
    component: Store
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
