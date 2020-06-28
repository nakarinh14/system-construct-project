<template>
  <div class="home">
    <component :is='assignComponent'></component>
  </div>
</template>

<script>
// @ is an alias to /src
import Courselist from '@/components/Courselist.vue'
import InstructorDashboard from "@/components/InstructorDashboard";
import Vue from "vue";

export default {
  name: 'Home',
  components: {
    Courselist,
    InstructorDashboard
  },
  computed:{
    assignComponent(){
      const user_type =  Vue.prototype.$session.get("user_type");
      if(user_type === "instructor"){
           return "InstructorDashboard"
      } else{
        return "Courselist"
      }
    }
  }
}
</script>
