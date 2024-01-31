<template>
  <v-container>
    <h2>포인트 환전 탭</h2>
    <v-row>
      <v-col cols="12" md="6">
        <!-- 환전 부분 -->
        <v-card style="text-align: center; height: 250px">
          <div style="color: #34a080">환전</div>
          <br />
          <br />
          <v-form @submit.prevent="exchangePoints">
            <v-row style="margin-left: 5px; margin-right: 5px">
              <v-col cols="12" md="6">
                <v-text-field v-model="pointAmount" label="포인트" type="number"></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field v-model="coinAmount" label="코인" type="number"></v-text-field>
              </v-col>
            </v-row>
            <br />
            <!-- 색상 변경된 버튼 -->
            <v-btn type="submit" color="#34A080">환전</v-btn>
          </v-form>
        </v-card>
      </v-col>
      <v-col cols="12" md="6">
        <!-- 실시간 차트 -->
        <v-card style="text-align: center">
          <div style="color: #34a080">환율 그래프</div>
          <br />
          <v-container class="chart-container">
            <canvas ref="lineChart" height="200px" width="600px"></canvas>
          </v-container>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

let chart;

export default {
  data() {
    return {
      pointAmount: 0,
      coinAmount: 0,
      chartData: {
        labels: [],
        datasets: [
          {
            label: 'Bitcoin 가격 (USD)',
            data: [],
            backgroundColor: [
              'rgba(0, 128, 128, 0.2)', // Teal
              'rgba(0, 128, 128, 0.2)', // Teal
            ],
            borderColor: [
              'rgba(0, 128, 128, 1)', // Teal
              'rgba(0, 128, 128, 1)', // Teal
            ],

            borderWidth: 1,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: true,
        scales: {
          y: {
            beginAtZero: false,
            precision: 0,
          },
        },
      },
    };
  },
  mounted() {
    this.createChart();
    // 3초마다 createChart 함수 호출
    setInterval(this.createChart, 3000);
  },
  methods: {
    async exchangePoints() {
      // 생략: 포인트 환전 처리 로직
    },
    async createChart() {
      try {
        const response = await axios.get('https://min-api.cryptocompare.com/data/price?fsym=USDT&tsyms=BNB');
        const timestamp = new Date().toLocaleTimeString();
        // console.log("USDT -> BNB : ", response.data.BNB)
        const randomIndex = Math.floor(Math.random() * 5);

        // 배열 [-10, -5, 0, 5, 10]에서 무작위로 선택된 값 반환
        const values = [-10, -5, 0, 5, 10];
        // console.log("value : ", values[randomIndex])
        const price = Math.ceil((response.data.BNB / 229.5) * 100000000) + values[randomIndex];

        // console.log("price : ", price)

        // 첫 실행 시에만 차트 인스턴스 생성
        if (!chart) {
          chart = new Chart(this.$refs.lineChart, {
            type: 'line',
            data: this.chartData,
            options: this.options,
          });
        }

        // ES6의 스프레드 연산자를 사용하여 새로운 배열을 생성
        this.chartData.labels = [...this.chartData.labels, timestamp];
        this.chartData.datasets[0].data = [...this.chartData.datasets[0].data, price];

        if (this.chartData.labels.length > 10) {
          this.chartData.labels = this.chartData.labels.slice(1);
          this.chartData.datasets[0].data = this.chartData.datasets[0].data.slice(1);
        }

        // 차트 업데이트
        chart.update();
      } catch (error) {
        console.error('환율 정보를 가져오는 중 오류 발생:', error);
      }
    },
  },
};
</script>

<style scoped>
/* 필요한 스타일이 있다면 추가 */
</style>
