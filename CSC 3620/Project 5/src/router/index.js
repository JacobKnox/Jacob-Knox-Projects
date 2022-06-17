import { createRouter, createWebHashHistory } from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import Projects from '../views/Projects.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/projects',
    name: 'Projects',
    component: Projects
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
