<template>
  <v-container>
    <v-row>
      <v-col cols="12" md="6">
        <!-- 환전 부분 -->
        <v-card style="text-align: center; height: 350px">
          <div style="color: #34a080; font-size: larger; font-weight: bold">환전</div>
          <br />
          <br />
          <v-row style="color: #34a080; font-weight: bold"
            ><v-col cols="12" md="6">포인트</v-col><v-col cols="12" md="6">코인</v-col></v-row
          >
          <br />

          <v-form @submit.prevent="exchangePoints">
            <v-row style="margin-left: 5px; margin-right: 5px">
              <v-col cols="12" md="6">
                <v-text-field
                  class="vtextfield"
                  variant="plain"
                  v-model="pointAmount"
                  label="포인트"
                  type="number"
                  @input="onPointInput"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field
                  class="vtextfield"
                  variant="plain"
                  v-model="coinAmount"
                  label="코인"
                  type="number"
                  @input="onCoinInput"
                ></v-text-field>
              </v-col>
            </v-row>
            <br />

            <div
              v-if="profile"
              style="margin-left: 30px; margin-right: 30px; display: flex; justify-content: space-between"
            >
              <span>보유 포인트: {{ profile.point }}</span>
              <!-- 색상 변경된 버튼 -->
              <span
                ><v-btn
                  @click="exchange"
                  type="submit"
                  color="#62C0A6"
                  size="x-large"
                  style="border-radius: 34px; font-size: large; width: 111px"
                  >환전</v-btn
                >
              </span>
              <span>보유 코인: {{ profile.coin }}</span>
            </div>
            <br />

            <div style="display: flex; justify-content: baseline; align-items: center; margin-left: 20px">
              <span
                ><img style="width: 30px" @mouseover="showMsg" @mouseleave="hideMsg" src="../../../helpicon.png"
              /></span>
              &nbsp;<span style="font-size: 13px" v-show="isHovered"
                >포인트는 매일 출석 체크, 질문, 답변, 채택, 신고 인정 시 획득 가능합니다.</span
              >
            </div>
          </v-form>
        </v-card>
      </v-col>

      <v-col cols="12" md="6">
        <!-- 실시간 차트 -->
        <v-card style="text-align: center; height: 350px">
          <div style="color: #34a080; font-size: larger; font-weight: bold">환율 그래프</div>
          <br />
          <v-container class="chart-container">
            <canvas ref="lineChart" height="100%"></canvas>
          </v-container>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
// import { ref, onBeforeMount } from 'vue';
// import { storeToRefs } from 'pinia';
import axios from 'axios';
// import { useUserStore } from '@/stores/userStore';
// import { useProfileStore } from '@/stores/profileStore';
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

let chart;

export default {
  // setup() {
  //   const userStore = useUserStore();
  //   const profileStore = useProfileStore();
  //   const { setUserProfile } = profileStore;
  //   const { handleUserProfile: profile } = storeToRefs(profileStore);
  // },
  data() {
    return {
      writeCoinInput: false,
      writePointInput: false,
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
      isHovered: false,
    };
  },
  // beforeMount() {
  //   const state = async () => {
  //     await this.setUserProfile(this.userStore.loginUserId); // 포인트, 코인 조회
  //   };
  //   state();
  // },
  mounted() {
    // 화면 이동 직후 바로 렌더링되지 않는 문제로 이전 차트 파괴
    if (chart) {
      chart.destroy();
    }

    // 새 차트 인스턴스 생성
    // this.createChart();
    chart = new Chart(this.$refs.lineChart, {
      type: 'line',
      data: this.chartData,
      options: this.options,
    });

    // 3초마다 createChart 함수 호출
    setInterval(this.createChart, 3000);
  },
  methods: {
    async exchangePoints() {
      // 포인트 환전 처리 로직
      const rate = this.chartData.datasets[0].data.slice(-1)[0];
      this.pointAmount = 0;
      this.coinAmount = 0;

      if (rate && !isNaN(rate)) {
        if (this.pointAmount) {
          this.coinAmount = Math.floor(this.pointAmount / rate);
        }
        if (this.coinAmount) {
          this.pointAmount = Math.floor(this.coinAmount * rate);
        }
      } else {
      }
    },
    async createChart() {
      try {
        const response = await axios.get('https://min-api.cryptocompare.com/data/price?fsym=USDT&tsyms=BNB');
        const timestamp = new Date().toLocaleTimeString();
        const randomIndex = Math.floor(Math.random() * 5);

        // 배열 [-10, -5, 0, 5, 10]에서 무작위로 선택된 값 반환
        const values = [-10, -5, 0, 5, 10];
        const price = Math.ceil((response.data.BNB / 229.5) * 100000000) + values[randomIndex];

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
      } catch (error) {}
    },
    onPointInput() {
      // 포인트 입력 값이 변경될 때 호출되는 메서드
      const rate = this.chartData.datasets[0].data.slice(-1)[0];
      if (rate && !isNaN(rate)) {
        this.coinAmount = Math.floor(this.pointAmount / rate);
        this.writePointInput = true;
        this.writeCoinInput = false;
      }
    },
    onCoinInput() {
      // 코인 입력 값이 변경될 때 호출되는 메서드
      const rate = this.chartData.datasets[0].data.slice(-1)[0];
      if (rate && !isNaN(rate)) {
        this.pointAmount = Math.floor(this.coinAmount * rate);
        this.writeCoinInput = true;
        this.writePointInput = false;
      }
    },
    exchange() {
      // 환전 버튼 누르면 환전이 이루어지는 메서드
      const rate = this.chartData.datasets[0].data.slice(-1)[0];
      if (rate && !isNaN(rate)) {
        if (this.coinAmount > 0 && this.writeCoinInput) {
          const minusCoins = Math.floor(this.coinAmount);
          const plusPoints = Math.floor(this.pointAmount);
          if (minusCoins > 0 && minusCoins <= this.profile.coin) {
            this.profile.coin -= minusCoins;
            this.profile.point += plusPoints;
          } else {
            alert('보유 코인이 부족합니다.');
          }
        } else if (this.pointAmount > 0 && this.writePointInput) {
          const minusPoints = Math.floor(this.pointAmount);
          const plusCoins = Math.floor(this.coinAmount);
          if (minusPoints > 0 && minusPoints <= this.profile.point) {
            this.profile.point -= minusPoints;
            this.profile.coin += plusCoins;
          } else {
            alert('보유 포인트가 부족합니다.');
          }
        }
      }
    },
    showMsg() {
      this.isHovered = true;
    },
    hideMsg() {
      this.isHovered = false;
    },
  },
};
</script>

<style scoped>
/* 필요한 스타일이 있다면 추가 */
.vtextfield {
  border-radius: 20px;
  background-color: #d9f2eb;
  box-shadow: 0 8px 0px rgba(0, 0, 0, 0.2);
  padding-left: 20px;
  padding-right: 20px;
  padding-top: 10px;
}

.v-text-field .v-input__control .v-input__slot::after {
  background-color: transparent;
}
</style>
