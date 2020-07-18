<template>
    <b-container fluid>
        <b-table striped bordered hover :head-variant="'dark'"
                 :items="data"
                 :fields="fields"
                 :filter="searchFilter"
                 @filtered="onFiltered"
                 :current-page="currentPage"
                 :per-page="perPage"
                 class="align-middle"
        >
            <template v-slot:cell(infos)="row">
                <b-modal
                        :id="row.item.id+'-modal'"
                        size="lg"
                >
                    <template v-slot:modal-title>
                        {{row.item.courseName}} (Section {{row.item.section}})
                    </template>
                    {{row.item.info}}
                </b-modal>
                <b-button v-b-modal="row.item.id+'-modal'" size="sm" class="mr-2">
                    Show Info
                </b-button>
            </template>

            <template v-slot:cell(seatAvailable)="row">
                {{row.item.capacity - row.item.registered}}
            </template>
        </b-table>
    </b-container>
</template>

<script>
    export default {
        name: "AdminCourseTable",
        props: ['data', 'busy', 'searchFilter', 'currentPage', 'perPage'],
        data() {
            return {
                fields: ["courseId","courseName","division","section",
                    "instructorName","capacity","registered","seatAvailable","date","infos"],
                info: "",
            }
        },
        methods:{
            onFiltered: function(filteredItems) {
                // Trigger pagination to update the number of buttons/pages due to filtering
                // Emit event back to DashboardComponent to update pagination with filtered length
                this.$emit('filterUpdated', filteredItems.length)
            }
        }
    }
</script>

<style scoped>

</style>