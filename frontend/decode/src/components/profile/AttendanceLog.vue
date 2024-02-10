<script setup>
import myaxios from '@/utils/common-axios.js';
import { ref, reactive, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

const id = route.params.id;

const now = new Date();
const attendanceLog = reactive([]);
const weeks = ref([]);
const tooltipDate = ref('');

const startDate = new Date(now.getFullYear(), now.getMonth() - 6, now.getDate());
const endDate = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 1);
splitIntoWeeks(startDate, endDate);

function splitIntoWeeks(startDate, endDate) {
  const weeksData = [];
  let currentDate = startDate;

  while (currentDate <= endDate) {
    const week = [];
    for (let i = 0; i < 7; i++) {
      week.push(currentDate.toISOString().slice(0, 10));
      currentDate.setDate(currentDate.getDate() + 1);
    }
    weeksData.push(week);
  }
  weeks.value = weeksData;
}

function isDayActive(date) {
  return attendanceLog.includes(date);
}

function hoverDay(date) {
  tooltipDate.value = date;
}

function hideTooltip() {
  tooltipDate.value = '';
}

// uid로 해당 유저의 출석 로그 API 호출
onMounted(() => {
  myaxios
    .get(`/attendance/${id}`)
    .then((res) => {
      res.data.data.forEach((date) => {
        attendanceLog.push(date);
      });
    })
    .catch((err) => {
      console.log(err);
    });
});
</script>

<template>
  <div class="attendance-heatmap">
    <h3>여기 회원님의 <span style="color: #34a080">'빈약한!'</span> 잔디밭을 보세요!</h3>

    <div style="display: flex">
      <div class="seven-days">
        <div>Mon</div>
        <div>Tue</div>
        <div>Wed</div>
        <div>Thu</div>
        <div>Fri</div>
        <div>Sat</div>
        <div>Sun</div>
      </div>
      <div class="heatmap-grid">
        <div v-for="(week, index) in weeks" :key="index" class="heatmap-week">
          <div v-for="(day, idx) in week" :key="idx" class="heatmap-day-wrapper">
            <div
              class="heatmap-day"
              :class="{ 'heatmap-day-active': isDayActive(day) }"
              @mouseover="hoverDay(day)"
              @mouseleave="hideTooltip"
            ></div>
            <div class="tooltip" v-show="tooltipDate === day">{{ tooltipDate }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.attendance-heatmap {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}

.heatmap-grid {
  display: flex;
  gap: 2px;
}
.seven-days {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-right: 10px;
  font-size: 10px;
  color: #34a080;
  font-weight: bolder;
  text-shadow: 1px 1px #62c0a6;
}

.heatmap-week {
  display: flex;
  flex-direction: column;
}

.heatmap-day-wrapper {
  position: relative;
}

.heatmap-day {
  width: 14px;
  height: 14px;
  background-color: rgb(226, 220, 220);
  margin-top: 2px;
  z-index: 1;
}

.heatmap-day-active {
  background-color: #34a080;
}

.tooltip {
  font-size: 12px;
  position: absolute;
  top: -25px;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba(22, 144, 120, 0.8);
  color: white;
  padding: 5px;
  border-radius: 5px;
  z-index: 2;
  pointer-events: none;
  width: 100px;
}
</style>
