<template>
    <div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        videoElement: null,
      };
    },
    mounted() {
      this.videoElement = this.$refs.videoElement;
    },
    methods: {
      async startScreenSharing() {
        try {
          const stream = await navigator.mediaDevices.getDisplayMedia({
            video: {
              cursor: "always"
            },
            audio: false
          });
          this.videoElement.srcObject = stream;
        } catch (err) {
          console.error("Error: " + err);
        }
      },
    },
  };
  </script>
  