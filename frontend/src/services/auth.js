import axios from 'axios';

const API_BASE = '/api'; // This will be proxied to your backend

export const authService = {
  async register(userData) {
    try {
      const response = await axios.post(`${API_BASE}/auth/register`, userData);
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data || 'Registration failed');
    }
  },
  
  async login(credentials) {
    try {
      const response = await axios.post(`${API_BASE}/auth/login`, credentials);
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data || 'Login failed');
    }
  },

  saveToken(token) {
    localStorage.setItem('authToken', token);
  },

  getToken() {
    return localStorage.getItem('authToken');
  },

  logout() {
    localStorage.removeItem('authToken');
  }
};