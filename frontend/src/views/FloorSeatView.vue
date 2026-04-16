<template>
  <div class="page">
    <h2>座位選擇系統</h2>
    <div class="assign-panel">
      <label>選擇員編：</label>

      <select v-model="empId">
        <option value="">請選擇員編</option>

        <option v-for="emp in employees" :key="emp.empId" :value="emp.empId">
          {{ emp.empId }} - {{ emp.name }}
        </option>
      </select>
    </div>
    <button class="submit-btn" @click="submitSeat">送出</button>

    <div class="current-seat" v-if="selectedSeat">
      目前選擇：
      {{ selectedSeat.floorNo }}樓:座位{{ selectedSeat.seatNo }}
    </div>

    <div class="grid">
      <div
        v-for="seat in seats"
        :key="seat.floorSeatSeq"
        class="seat"
        :class="{
          taken: seat.empId,
          selected: selectedSeat && selectedSeat.floorSeatSeq === seat.floorSeatSeq,
        }"
        @click="selectSeat(seat)"
      >
        {{ seat.floorNo }}樓:座位{{ seat.seatNo }}
        <span v-if="seat.empId">[員編:{{ seat.empId }}]</span>
      </div>
    </div>

    <div class="legend">
      <div class="legend-item">
        <div class="legend-box empty"></div>
        <span>空位</span>
      </div>

      <div class="legend-item">
        <div class="legend-box selected"></div>
        <span>已選擇</span>
      </div>

      <div class="legend-item">
        <div class="legend-box taken"></div>
        <span>已佔用</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const employees = ref([])
const empId = ref('')
const seats = ref([])
const selectedSeat = ref(null)

onMounted(() => {
  loadEmployees()
  loadSeats()
})

function loadEmployees() {
  fetch('http://localhost:8080/api/seats/employees')
    .then((res) => res.json())
    .then((data) => {
      employees.value = data
    })
}

function loadSeats() {
  fetch('http://localhost:8080/api/seats')
    .then((res) => res.json())
    .then((data) => {
      seats.value = data
    })
}

function selectSeat(seat) {
  selectedSeat.value = seat
}

function submitSeat() {
  if (!empId.value) {
    alert('請選擇員編')
    return
  }

  if (!selectedSeat.value) {
    alert('請選擇座位')
    return
  }

  fetch('http://localhost:8080/api/seats/action', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      empId: empId.value,
      seatSeq: selectedSeat.value.floorSeatSeq,
    }),
  })
    .then((res) => res.json())
    .then((data) => {
      if (data.status === 'SUCCESS') {
        selectedSeat.value = null
        loadSeats()
      }
      alert(data.message)
    })
}
</script>

<style>
body{
  background: #f8fafc;
}
.page {
  padding: 30px;
  font-family: Arial, sans-serif;
}

h2 {
  margin-bottom: 20px;
}

.assign-panel {
  margin-bottom: 20px;
}

select {
  padding: 6px 10px;
  margin-left: 10px;
}

.submit-btn {
  margin-bottom: 20px;
  padding: 8px 20px;
  border-radius: 6px;
  border: none;
  background: #3b82f6;
  color: white;
  cursor: pointer;
}

.submit-btn:hover {
  background: #2563eb;
}

.current-seat {
  margin-bottom: 20px;
}

.grid {
  display: grid;
  grid-template-columns: repeat(4, 200px);
  gap: 15px;
  margin-bottom: 30px;
}

.seat {
  border: 1px solid #ccc;
  height: 80px;
  width: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f5f5;
  border-radius: 10px;
  padding: 10px;
  text-align: center;
  transition: 0.2s;
}

.seat:hover {
  transform: scale(1.05);
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.15);
  cursor: pointer;
}

.legend {
  margin-top: 10px;
}

.taken {
  background: #ef4444;
  color: white;
}

.selected {
  background: #22c55e;
  color: white;
}

.legend-item {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.legend-box {
  width: 20px;
  height: 20px;
  border-radius: 6px;
  margin-right: 10px;
}

.legend-box.empty {
  background: #f5f5f5;
  border: 1px solid #ccc;
}

.legend-box.selected {
  background: #22c55e;
}

.legend-box.taken {
  background: #ef4444;
}
</style>
