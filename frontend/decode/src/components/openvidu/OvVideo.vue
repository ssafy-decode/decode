<template>
    <video ref="videoEl" autoplay />
  </template>
  
  <script>
    export default {
      name: 'OvVideo',
    }
  </script>
  
  <script setup>
  import { ref, onMounted } from "vue";
  
  const props = defineProps({
    streamManager: Object,
  });
  
  const videoEl = ref(null);
  
  // mouted되면 videoEl의 값을 addVideoElement에 추가함.
  onMounted(() => {
    props.streamManager.addVideoElement(videoEl.value);
  });
  </script>
  
  
  <!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  -->
  <!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  -->
  <!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  --><!--  -->
  
  <!-- <template>
      <video autoplay/>
  </template>
  
  <script>
  export default {
      name: 'OvVideo',
  
      props: {
          streamManager: Object,
      },
  
      mounted () {
          this.streamManager.addVideoElement(this.$el);
      },
  };
  </script> -->
  