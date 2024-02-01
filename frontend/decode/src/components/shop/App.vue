<template>
  <v-card>
    <v-tabs v-model="tab" bg-color="#34A080">
      <v-tab value="exchange">환전</v-tab>
      <v-tab value="shop">상점</v-tab>
    </v-tabs>

    <v-card-text>
      <v-window v-model="tab">
        <v-window-item value="exchange">
          <ExchangeTab />
        </v-window-item>

        <v-window-item value="shop">
          <ShopTab />
        </v-window-item>
      </v-window>
    </v-card-text>
  </v-card>

  <Chat></Chat>
</template>

<script>
import ExchangeTab from '@/components/shop/ExchangeTab.vue';
import ShopTab from '@/components/shop/ShopTab.vue';
import Chat from '@/components/chat/Chat.vue';

export default {
  components: {
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
<style></style>
