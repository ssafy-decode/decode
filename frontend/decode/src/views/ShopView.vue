<template>
  <v-container class="itemshop">
    <ExchangeTab />
    <ShopTab />
  </v-container>
  <!-- <Chat></Chat> -->
</template>

<script>
import ExchangeTab from '@/components/shop/ExchangeTab.vue';
import ShopTab from '@/components/shop/ShopTab.vue';
// import Chat from '@/components/chat/Chat.vue';
export default {
  components: {
    ExchangeTab,
    ShopTab,
    // Chat,
  },
  methods: {},
  data: () => ({
    tab: 'exchange',
  }),
};
</script>
<style></style>
