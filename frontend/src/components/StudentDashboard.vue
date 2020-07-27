<template>
    <div>
        <b-container fluid>
            <b-table striped bordered hover
                     :busy="busy"
                     :head-variant="'dark'"
                     :items="data"
                     :fields="fields"
                     :filter="searchFilter"
                     :current-page="currentPage"
                     :per-page="perPage"
                     @filtered="onFiltered"
            >
                <template v-slot:table-busy>
                    <div class="text-center text-primary my-2">
                        <b-spinner class="align-middle"></b-spinner>
                        <strong>Loading...</strong>
                    </div>
                </template>
                <template v-slot:cell(infos)="row">
                    <b-modal scrollable
                            :id="row.item.id+'-modal'"
                            size="lg"
                             ok-only
                    >
                        <template v-slot:modal-title>
                            {{row.item.courseName}} (Section {{row.item.section}})
                        </template>
                        {{row.item.info}}
                        <template v-slot:modal-ok>
                            Done
                        </template>
                    </b-modal>
                    <b-button v-b-modal="row.item.id+'-modal'"
                              size="sm"
                              variant="primary"
                    >
                        Show Info
                    </b-button>
                </template>

                <template v-slot:cell(seatAvailable)="row">
                    {{row.item.capacity - row.item.registered}}
                </template>
            </b-table>
        </b-container>
    </div>
</template>


<script>
    export default {
        name: 'StudentDashboard',
        props: ['data', 'busy', 'searchFilter', 'currentPage', 'perPage'],
        data() {
            return {
                fields: [
                    {key:"courseId", sortable:true, label:"ID"},
                    {key:"courseName", sortable:true},
                    {key:"division", sortable:true},
                    {key:"section", sortable:true},
                    {key:"instructorName", sortable:true},
                    {key:"capacity", sortable:true},
                    {key:"registered", sortable:true},
                    {key:"seatAvailable", sortable: true},
                    {key:"date"},
                    {key:"infos", label:"Info"}
                ],
                info: "",
            }
        },
        methods:{
            onFiltered: function(filteredItems) { // Trigger pagination to update the number of buttons/pages due to filtering
                // Emit event back to DashboardComponent to update pagination with filtered length
                this.$emit('filterUpdated', filteredItems.length)
            }
        }
    };
</script>




