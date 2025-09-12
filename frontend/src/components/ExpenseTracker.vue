<template>
  <div class="max-w-3xl mx-auto p-6 mt-10 bg-gray-50 rounded-lg shadow-md">

    <!-- Landing Page -->
    <div v-if="!showExpenses">
      <h1 class="text-3xl font-bold mb-6 text-green-600">💰 Expense Tracker</h1>

      <form @submit.prevent="addExpense" class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
        <input v-model="description" type="text" placeholder="Description"
               class="border p-2 rounded focus:outline-none focus:ring-2 focus:ring-green-400" required />

        <input v-model.number="amount" type="number" placeholder="Amount"
               class="border p-2 rounded focus:outline-none focus:ring-2 focus:ring-green-400" required />

        <select v-model="category" class="border p-2 rounded focus:outline-none focus:ring-2 focus:ring-green-400">
          <option>Food</option>
          <option>Travel</option>
          <option>Entertainment</option>
          <option>Other</option>
        </select>

        <button type="submit"
                class="bg-green-600 text-white rounded p-2 hover:bg-green-700 transition-colors font-semibold">
          Add
        </button>
      </form>

      <button @click="showExpenses = true"
              class="fixed top-4 right-4 bg-blue-600 text-white p-2 rounded hover:bg-blue-700 transition-colors">
        View Expenses
      </button>
    </div>

    <!-- Expenses Page -->
    <div v-else>
      <button @click="showExpenses = false"
              class="mb-4 bg-gray-300 text-black p-2 rounded hover:bg-gray-400 transition-colors">
        ← Back
      </button>

      <h2 class="text-2xl font-bold mb-4">All Expenses</h2>

      <div class="overflow-x-auto">
        <table class="w-full border-collapse shadow-sm">
          <thead>
            <tr class="bg-green-100">
              <th class="p-2 border">Description</th>
              <th class="p-2 border">Amount</th>
              <th class="p-2 border">Category</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(expense, index) in expenses" :key="index" class="hover:bg-green-50">
              <td class="p-2 border">{{ expense.description }}</td>
              <td class="p-2 border">${{ expense.amount }}</td>
              <td class="p-2 border">{{ expense.category }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// State
const description = ref('')
const amount = ref(0)
const category = ref('Food')
const expenses = ref([])
const showExpenses = ref(false)

// Fetch expenses from backend
const fetchExpenses = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/expenses')
    expenses.value = res.data
  } catch (err) {
    console.error(err)
  }
}

// Add expense
const addExpense = async () => {
  if (!description.value || amount.value <= 0) return

  try {
    const res = await axios.post('http://localhost:8080/api/expenses', {
      description: description.value,
      amount: amount.value,
      category: category.value
    })
    expenses.value.push(res.data)
    description.value = ''
    amount.value = 0
    category.value = 'Food'
  } catch (err) {
    console.error(err)
  }
}

onMounted(fetchExpenses)
</script>

<style scoped>
tr { transition: background-color 0.3s ease; }
</style>
