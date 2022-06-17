<template>
  <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
      <ul class="navbar-nav">
        <li class="nav-item">
            <router-link to="/" class="nav-link">Home</router-link>
        </li>
        <li class="nav-item">
            <router-link to="/about" class="nav-link">About</router-link>
        </li>
        <li class="nav-item">
            <router-link to="/projects" class="nav-link">Projects</router-link>  
        </li>
      </ul>
  </nav>
</template>

<script>

export default {
  name: 'Header'
}
</script>
