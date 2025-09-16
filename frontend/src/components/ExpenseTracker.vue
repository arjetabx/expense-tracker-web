<template>
  <div class="min-h-screen bg-gray-100 flex flex-col items-center p-6">
    <h1 class="text-3xl font-bold mb-6">💰 Expense Tracker</h1>

    <!-- Add expense form -->
    <div class="bg-white p-6 rounded-2xl shadow w-full max-w-md mb-6">
      <input
        v-model="newExpense.name"
        type="text"
        placeholder="Expense name"
        class="w-full mb-3 p-2 border rounded"
      />
      <input
        v-model.number="newExpense.amount"
        type="number"
        placeholder="Amount"
        class="w-full mb-3 p-2 border rounded"
      />
      <select
        v-model="newExpense.category"
        class="w-full mb-3 p-2 border rounded"
      >
        <option disabled value="">Select category</option>
        <option>Food</option>
        <option>Travel</option>
        <option>Shopping</option>
        <option>Entertainment</option>
        <option>Other</option>
      </select>
      <button
        @click="addExpense"
        class="w-full bg-blue-600 text-white p-2 rounded hover:bg-blue-700 transition"
      >
        ➕ Add Expense
      </button>
    </div>

    <!-- Toggle expenses view -->
    <button
      @click="toggleExpenses"
      class="bg-green-600 text-white px-4 py-2 rounded shadow hover:bg-green-700 transition"
    >
      {{ showExpenses ? "Hide Expenses" : "View Expenses" }}
    </button>

    <!-- Expenses list -->
    <div
      v-if="showExpenses"
      class="mt-6 w-full max-w-3xl bg-white p-6 rounded-2xl shadow"
    >
      <h2 class="text-xl font-semibold mb-4">📊 Expenses by Category</h2>

      <div v-for="(items, category) in groupedExpenses" :key="category" class="mb-6">
        <h3 class="text-lg font-bold mb-2">{{ category }} (Total: £{{ categoryTotal(items) }})</h3>
        <table class="w-full text-left border">
          <thead class="bg-gray-200">
            <tr>
              <th class="p-2">Name</th>
              <th class="p-2">Amount</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="expense in items" :key="expense.id" class="border-t">
              <td class="p-2">{{ expense.name }}</td>
              <td class="p-2">£{{ expense.amount }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue"

const expenses = ref([])
const newExpense = ref({ name: "", amount: null, category: "" })
const showExpenses = ref(false)

function addExpense() {
  if (!newExpense.value.name || !newExpense.value.amount || !newExpense.value.category) {
    alert("Please fill in all fields")
    return
  }
  expenses.value.push({
    id: Date.now(),
    name: newExpense.value.name,
    amount: newExpense.value.amount,
    category: newExpense.value.category,
  })
  newExpense.value = { name: "", amount: null, category: "" }
}

function toggleExpenses() {
  showExpenses.value = !showExpenses.value
}

// Group expenses by category
const groupedExpenses = computed(() => {
  return expenses.value.reduce((groups, expense) => {
    if (!groups[expense.category]) {
      groups[expense.category] = []
    }
    groups[expense.category].push(expense)
    return groups
  }, {})
})

function categoryTotal(items) {
  return items.reduce((sum, exp) => sum + exp.amount, 0)
}
</script>

<style>
body {
  font-family: system-ui, sans-serif;
}
</style>
