<template>
    <v-card v-if="product && product.category !== undefined">
      <v-card-title>{{ product.name }}</v-card-title>
      <v-card-subtitle>{{ product.description }}</v-card-subtitle>
      <!-- Add more details or customize as needed -->
    </v-card>
  </template>
  
  <script>
  export default {
    props: {
      product: {
        type: Object,
        required: true,
      },
    },
  };
  </script>
  
  <style scoped>
    /* Add styling if needed */
  </style>
  