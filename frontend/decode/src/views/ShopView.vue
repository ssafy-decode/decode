<template>
  <v-container class="itemshop">
    <ExchangeTab />
    <br />
    <ShopTab />
  </v-container>
</template>

<script>
import ExchangeTab from '@/components/shop/ExchangeTab.vue';
import ShopTab from '@/components/shop/ShopTab.vue';
export default {
  components: {
    ExchangeTab,
    ShopTab,
  },
  methods: {},
  data: () => ({
    tab: 'exchange',
  }),
};
</script>
<style></style>
