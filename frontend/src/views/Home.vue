<template>
  <div class="home">
    <Courselist />
  </div>
</template>

<script>
// @ is an alias to /src
import Courselist from '@/components/Courselist.vue'

export default {
  name: 'Home',
  components: {
    Courselist
  }
}
</script>
