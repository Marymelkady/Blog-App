<!-- src/components/RegisterView.vue -->
<template>
  <div class="auth-container">
    <h2>Register</h2>
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label>Username:</label>
        <input v-model="username" required>
      </div>
      <div class="form-group">
        <label>Email:</label>
        <input v-model="email" type="email" required>
      </div>
      <div class="form-group">
        <label>Password:</label>
        <input v-model="password" type="password" required>
      </div>
      <div class="form-group">
        <label>Confirm Password:</label>
        <input v-model="confirmPassword" type="password" required>
      </div>
      <button type="submit" class="btn" :disabled="loading">
        {{ loading ? 'Creating Account...' : 'Register' }}
      </button>
      <p v-if="error" class="error">{{ error }}</p>
      <p v-if="success" class="success">{{ success }}</p>
      <p class="auth-link">
        Already have an account? 
        <a href="#" @click.prevent="$emit('navigate-to-login')">Login here</a>
      </p>
    </form>
  </div>
</template>

<script>
export default {
  name: 'RegisterView',
  emits: ['register-success', 'navigate-to-login'],
  data() {
    return {
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      loading: false,
      error: '',
      success: ''
    };
  },
  methods: {
    async handleRegister() {
      this.loading = true;
      this.error = '';
      this.success = '';
      
      // Basic validation
      if (this.password !== this.confirmPassword) {
        this.error = 'Passwords do not match';
        this.loading = false;
        return;
      }
      
      try {
        // Use mock authService
        const response = await this.authService.register({
          username: this.username,
          email: this.email,
          password: this.password
        });
        
        // Save to local storage to maintain session
        localStorage.setItem('authToken', response.token);
        localStorage.setItem('userData', JSON.stringify({
          username: this.username
        }));
        
        this.success = 'Registration successful!';
        this.$emit('register-success', { username: this.username });
        
      } catch (error) {
        this.error = error.message;
      } finally {
        this.loading = false;
      }
    },
    
    // Mock authService implementation
    authService: {
      async register(userData) {
        // Simulate API call delay
        await new Promise(resolve => setTimeout(resolve, 1000));
        
        // Mock registration - always succeeds for demo
        return { 
          token: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkFkbWluIFVzZXIiLCJpYXQiOjE1MTYyMzkwMjJ9',
          user: {
            id: Math.random().toString(36).substr(2, 9),
            username: userData.username,
            email: userData.email
          }
        };
      }
    }
  }
};
</script>

<style scoped>
.auth-container {
  max-width: 500px;
  margin: 0 auto;
}
h2 {
  text-align: center;
  margin-bottom: 25px;
  color: #4a5568;
}
.form-group {
  margin-bottom: 20px;
}
label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #4a5568;
}
input {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid #cbd5e0;
  border-radius: 6px;
  font-size: 16px;
  transition: border-color 0.3s;
}
input:focus {
  outline: none;
  border-color: #4299e1;
  box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.2);
}
button.btn {
  width: 100%;
  padding: 12px;
  background: #667eea;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.3s;
}
button.btn:hover {
  background: #5a67d8;
}
button.btn:disabled {
  background: #a0aec0;
  cursor: not-allowed;
}
.error {
  color: #e53e3e;
  margin-top: 1rem;
  text-align: center;
  padding: 10px;
  background: #fed7d7;
  border-radius: 6px;
}
.success {
  color: #38a169;
  margin-top: 1rem;
  text-align: center;
  padding: 10px;
  background: #f0fff4;
  border-radius: 6px;
}
.auth-link {
  text-align: center;
  margin-top: 20px;
}
.auth-link a {
  color: #667eea;
  text-decoration: none;
}
.auth-link a:hover {
  text-decoration: underline;
}
</style>