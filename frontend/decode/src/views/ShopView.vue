<template>
  <v-container class="itemshop">
    <ExchangeTab />
    <br />
    <ShopTab />
  </v-container>
  <Chat></Chat>
  <!-- <OpenviduModal></OpenviduModal> -->
</template>

<script>
import ExchangeTab from '@/components/shop/ExchangeTab.vue';
import ShopTab from '@/components/shop/ShopTab.vue';
import Chat from '@/components/chat/Chat.vue';
import OpenviduModal from '@/components/chat/OpenviduModal.vue';

export default {
  components: {
    OpenviduModal,
    ExchangeTab,
    ShopTab,
    Chat,
  },
  methods: {},
  data: () => ({
    tab: 'exchange',
  }),
};
</script>
<style scoped>
.custom-openvidu-modal {
  background-color: red;
  width: 100%;
  height: 100%;
}
</style>
