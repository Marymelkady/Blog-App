import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'  // Add the full path
import './assets/main.css'

const app = createApp(App)
app.use(router)
app.mount('#app')