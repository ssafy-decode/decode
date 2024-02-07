<template>
    <div v-if="streamManager">
      <ov-video :stream-manager="streamManager"/>
      <!-- {{ clientData }}이게 내 현재 이름임 -->
      <div><p>{{ clientData }}</p></div>
    </div>
  </template>
  
  <script>
    export default {
      name: 'UserVideo',
    }
  </script>
  
  <script setup>
    // import { ref, onMounted, computed } from 'vue';
    import { computed } from 'vue';
    import OvVideo from './OvVideo.vue';
  
  
    const props = defineProps({
      streamManager: Object,
    })
  
    // const streamManager = ref(Object);
    // onMounted(() => {
    //   clientData.value = getConnectionData();
    // });
  
    // clientData는 computed로 진행됨
    const clientData = computed(() => {
      const { clientData } = getConnectionData();
      return clientData;
    });
  
    function getConnectionData() {
      const { connection } = props.streamManager.stream;
      return JSON.parse(connection.data);
    }
  </script>
    
  
    
    <!-- <template>
        <div v-if="streamManager">
            <ov-video :stream-manager="streamManager"/>
            <div><p>{{ clientData }}</p></div>
        </div>
        </template>
        
        <script>
        import OvVideo from './OvVideo';
        
        export default {
            name: 'UserVideo',
        
            components: {
                OvVideo,
            },
        
            props: {
                streamManager: Object,
            },
        
            computed: {
                clientData () {
                    const { clientData } = this.getConnectionData();
                    return clientData;
                },
            },
        
            methods: {
                getConnectionData () {
                    const { connection } = this.streamManager.stream;
                    return JSON.parse(connection.data);
                },
            },
        };
        </script>
         -->