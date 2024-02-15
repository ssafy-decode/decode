<template>
  <div>
    <canvas id="expChart" width="400" height="400"></canvas>
  </div>
</template>

<script setup>
import myaxios from '@/utils/common-axios.js';
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { Chart } from 'chart.js';

const route = useRoute();
let chart;
// 차트 데이터를 저장할 배열
const expLog = ref([]);

// 차트 초기화 함수
const initChart = () => {
  // 차트를 렌더링할 캔버스 엘리먼트 가져오기
  const ctx = document.getElementById('expChart').getContext('2d');
  // Chart.js를 사용하여 차트 생성
  if (chart) chart.destroy();
  chart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: expLog.value.map((log) => {
        const month = log.date.getMonth() + 1;
        return `${log.date.getFullYear()}년 ${month}월`;
      }),
      datasets: [
        {
          label: 'EXP',
          data: expLog.value.map((log) => log.exp),
          borderColor: 'rgb(75, 192, 192)',
        },
      ],
    },
    options: {
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            stepSize: 100,
          },
        },
      },
    },
  });
};

// 경험치 로그 데이터를 가져와 차트를 초기화하는 함수
const fetchExpData = async (uid) => {
  try {
    const res = await myaxios.get(`/exp/${uid}`);
    const data = res.data.data;

    // 데이터를 배열로 변환하여 저장
    const expData = Object.entries(data).map(([date, exp]) => ({
      date: new Date(date),
      exp: parseInt(exp),
    }));

    // 날짜를 달 별로 그룹화하여 누적합 계산
    const monthlyExp = {};
    expData.forEach((log) => {
      const monthYear = log.date.getFullYear() + '-' + (log.date.getMonth() + 1);
      if (!monthlyExp[monthYear]) {
        monthlyExp[monthYear] = {
          date: new Date(log.date.getFullYear(), log.date.getMonth(), 1),
          exp: 0,
        };
      }
      monthlyExp[monthYear].exp += log.exp;
    });

    // 그룹화된 데이터를 배열로 변환하고 날짜 순으로 정렬
    expLog.value = Object.values(monthlyExp);
    expLog.value.sort((a, b) => a.date - b.date);

    // 경험치를 누적합으로 계산
    let sum = 0;
    expLog.value.forEach((log) => {
      sum += log.exp;
      log.exp = sum;
    });
    // 차트 초기화
    initChart();
  } catch (error) {}
};
watch(
  route,
  () => {
    expLog.value = [];
    fetchExpData(route.params.id);
  },
  {
    immediate: true,
    deep: true,
  },
);
</script>

<style scoped>
#expChart {
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
}
</style>
