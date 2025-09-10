<!-- src/components/TodoList.vue -->
<template>
  <div class="todo-container">
    <h2>My Todo List</h2>
    
    <div class="input-group">
      <input 
        v-model="newTodo" 
        @keyup.enter="addTodo" 
        placeholder="What needs to be done?" 
        maxlength="100"
      >
      <button @click="addTodo">Add</button>
    </div>

    <ul class="todo-list" v-if="filteredTodos.length">
      <li 
        v-for="todo in filteredTodos" 
        :key="todo.id" 
        :class="['todo-item', { completed: todo.completed }]"
      >
        <input 
          type="checkbox" 
          class="todo-checkbox" 
          v-model="todo.completed"
        >
        <span class="todo-text">{{ todo.text }}</span>
        <button class="todo-delete" @click="removeTodo(todo.id)">×</button>
      </li>
    </ul>

    <div v-else class="empty-state">
      <p>Your todo list is empty. Add a task to get started!</p>
    </div>

    <div class="stats">
      <span>{{ activeTodosCount }} items left</span>
      <button 
        v-if="completedTodosCount > 0" 
        class="clear-completed" 
        @click="clearCompleted"
      >
        Clear completed
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TodoList',
  data() {
    return {
      newTodo: '',
      todos: []
    };
  },
  computed: {
    filteredTodos() {
      return this.todos;
    },
    activeTodosCount() {
      return this.todos.filter(todo => !todo.completed).length;
    },
    completedTodosCount() {
      return this.todos.filter(todo => todo.completed).length;
    }
  },
  methods: {
    addTodo() {
      if (this.newTodo.trim()) {
        this.todos.push({
          id: Date.now(),
          text: this.newTodo.trim(),
          completed: false
        });
        this.newTodo = '';
        this.saveToLocalStorage();
      }
    },
    removeTodo(id) {
      this.todos = this.todos.filter(todo => todo.id !== id);
      this.saveToLocalStorage();
    },
    clearCompleted() {
      this.todos = this.todos.filter(todo => !todo.completed);
      this.saveToLocalStorage();
    },
    saveToLocalStorage() {
      const userData = JSON.parse(localStorage.getItem('userData') || '{}');
      localStorage.setItem(`todos-${userData.username}`, JSON.stringify(this.todos));
    },
    loadFromLocalStorage() {
      const userData = JSON.parse(localStorage.getItem('userData') || '{}');
      const storedTodos = localStorage.getItem(`todos-${userData.username}`);
      if (storedTodos) {
        this.todos = JSON.parse(storedTodos);
      }
    }
  },
  watch: {
    todos: {
      handler() {
        this.saveToLocalStorage();
      },
      deep: true
    }
  },
  mounted() {
    this.loadFromLocalStorage();
  }
};
</script>

<style scoped>
.todo-container {
  max-width: 500px;
  margin: 0 auto;
}
h2 {
  text-align: center;
  margin-bottom: 25px;
  color: #4a5568;
}
.input-group {
  display: flex;
  margin-bottom: 20px;
}
.input-group input {
  flex: 1;
  padding: 12px 15px;
  border: 1px solid #cbd5e0;
  border-radius: 6px 0 0 6px;
  font-size: 16px;
  transition: border-color 0.3s;
}
.input-group input:focus {
  outline: none;
  border-color: #4299e1;
  box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.2);
}
.input-group button {
  padding: 12px 20px;
  background: #667eea;
  color: white;
  border: none;
  border-radius: 0 6px 6px 0;
  cursor: pointer;
  font-weight: 600;
  transition: background 0.3s;
}
.input-group button:hover {
  background: #5a67d8;
}
.todo-list {
  list-style: none;
  margin-top: 20px;
}
.todo-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #e2e8f0;
  animation: fadeIn 0.5s;
}
.todo-item:last-child {
  border-bottom: none;
}
.todo-item.completed .todo-text {
  text-decoration: line-through;
  color: #a0aec0;
}
.todo-checkbox {
  margin-right: 12px;
  width: 20px;
  height: 20px;
  cursor: pointer;
}
.todo-text {
  flex: 1;
  word-break: break-word;
}
.todo-delete {
  background: none;
  border: none;
  color: #e53e3e;
  cursor: pointer;
  padding: 5px;
  font-size: 16px;
  transition: transform 0.3s;
}
.todo-delete:hover {
  transform: scale(1.2);
}
.empty-state {
  text-align: center;
  color: #a0aec0;
  padding: 40px 0;
}
.stats {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  padding-top: 15px;
  border-top: 1px solid #e2e8f0;
  color: #718096;
  font-size: 14px;
}
.clear-completed {
  background: none;
  border: none;
  color: #e53e3e;
  cursor: pointer;
  font-size: 14px;
  text-decoration: underline;
}
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>