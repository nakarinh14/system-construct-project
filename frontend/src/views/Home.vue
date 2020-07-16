<template>
    <component :is="component_name"></component>
</template>

<script>
    // @ is an alias to /src
    import DashboardComponent from '@/components/DashboardComponent.vue'
    import AdminSettingComponent from '@/components/AdminSettingComponent.vue';

    export default {
        name: 'Home',
        components: {
            DashboardComponent,
            AdminSettingComponent,
        },
        data(){
            return {
                component_name: null
            }
        },
        methods:{
            check_component: function(){
                switch(this.$cookies.get('role')){
                    case 'admin':
                        this.component_name = AdminSettingComponent;
                        break;
                    case 'instructor':
                    case 'student':
                        this.component_name = DashboardComponent;
                        break;
                    default:
                        this.component_name = null;

                }
            }
        },
        created() {
            this.check_component();
        }

    }
</script>
