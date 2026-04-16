<template>
  <div class="assign-panel">
    <label>選擇員編：</label>

    <select v-model="empId">
      <option value="">請選擇員編</option>

      <option v-for="emp in employees" :key="emp.empId" :value="emp.empId">
        {{ emp.empId }} - {{ emp.name }}
      </option>
    </select>
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

  <br />
  <button @click="submitSeat">送出</button>
  <br />
  <div v-if="selectedSeat">
    目前選擇：
    {{ selectedSeat.floorNo }}樓:座位{{ selectedSeat.seatNo }}
  </div>

  <div class="legend">
    <span class="empty">空位</span>
    <span class="taken">已佔用</span>
    <span class="selected">已選擇</span>
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

  fetch("http://localhost:8080/api/seats/action", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      empId: empId.value,
      seatSeq: selectedSeat.value.floorSeatSeq
    })
  })
    .then(res => res.json())
    .then(data => {
      if (data.status === "SUCCESS") {
        selectedSeat.value = null
        loadSeats()
      }
      alert(data.message)
    })
}


</script>

<style>
.assign-panel {
  margin: 15px 0;
}

select {
  padding: 6px;
  margin-left: 10px;
}

.grid {
  display: grid;
  grid-template-columns: repeat(4, 200px);
  gap: 10px;
}

.seat {
  border: 1px solid #ccc;
  height: 80px;
  width: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #eee;
  border-radius: 10px;
  padding: 5px;
  cursor: pointer;
}

.seat:hover {
  transform: scale(1.05);
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.2);
  cursor: pointer;
  transition: 0.2s;
}

.legend {
  padding: 5px;
}

.taken {
  background: red;
  color: white;
}

.selected {
  background: green;
  color: white;
}
</style>
