<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <nav>
      <RouterLink to="/">Home</RouterLink> |
      <RouterLink to="/login">Login</RouterLink>
    </nav>
  </header>

  <RouterView />
</template>

<style scoped>
header {
  line-height: 1.5;
}

nav {
  text-align: center;
  margin: 2rem 0;
}

nav a {
  color: #42b883;
  text-decoration: none;
  padding: 0 1rem;
}

nav a:hover {
  text-decoration: underline;
}
</style>