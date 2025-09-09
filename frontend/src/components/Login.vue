<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <div>
        <label>Username:</label>
        <input v-model="username" required>
      </div>
      <div>
        <label>Password:</label>
        <input v-model="password" type="password" required>
      </div>
      <button type="submit" :disabled="loading">
        {{ loading ? 'Logging in...' : 'Login' }}
      </button>
      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </div>
</template>

<script>
import { authService } from '@/services/auth';

export default {
  name: 'LoginView',
  data() {
    return {
      username: '',
      password: '',
      loading: false,
      error: ''
    };
  },
  methods: {
    async handleLogin() {
      this.loading = true;
      this.error = '';
      
      try {
        const response = await authService.login({
          username: this.username,
          password: this.password
        });
        
        authService.saveToken(response.token);
        alert('Login successful! Token: ' + response.token);
        // We'll add navigation to dashboard later
        
      } catch (error) {
        this.error = error.message;
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 2rem auto;
  padding: 2rem;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.error {
  color: red;
  margin-top: 1rem;
}
</style>