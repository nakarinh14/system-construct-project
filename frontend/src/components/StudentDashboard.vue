<template>
    <div>
        <b-container style="margin-bottom: 20px" fluid>
            <b-row cols="3">
                <b-col  class="d-flex justify-content-start">
                    <div>
                        <b>Show</b>
                            <b-form-select
                                    v-model="perPage"
                                    id="perPageSelect"
                                    size="sm"
                                    :options="pageOptions"
                                    style="width: 65px"
                            ></b-form-select>
                        <b>entries</b>

                    </div>
                </b-col>
                <b-col>
                    <b-input-group size="sm">
                        <b-input-group-prepend>
                                <span class="input-group-text">
                                    <BIconSearch class="icon-bar"></BIconSearch>
                                </span>
                        </b-input-group-prepend>
                        <b-form-input
                                v-model="search_filter"
                                placeholder="Search Courses"
                                type="search"
                                class="search-bar"
                        >
                        </b-form-input>
                    </b-input-group>
                </b-col>
            </b-row>
        </b-container>
        <b-container fluid>
            <b-table striped bordered hover
                     :busy="busy"
                     :head-variant="'dark'"
                     :items="course"
                     :fields="fields"
                     :filter="search_filter"
                     :current-page="currentPage"
                     :per-page="perPage"
                     @filtered="onFiltered"
            >
                <template v-slot:table-busy>
                    <div class="text-center text-danger my-2">
                        <b-spinner class="align-middle"></b-spinner>
                        <strong>Loading...</strong>
                    </div>
                </template>
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
        <b-container style="margin-top: 15px" fluid>
            <b-row>
                <b-col></b-col>
                <b-col>
                    <span>{{currentPageCount}}</span>
                </b-col>
                <b-col>
                    <b-pagination
                            v-model="currentPage"
                            :per-page="perPage"
                            :total-rows="rows"
                            aria-controls="my-table"
                            size="sm"
                            class="justify-content-end"
                            prev-text="Prev"
                            next-text="Next"
                    ></b-pagination>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>


<script>
    export default {
        name: 'StudentDashboard',
        props: ['course', 'busy'],
        computed: {
          currentPageCount: function(){
              let minBound = 1 + ((this.currentPage-1)*this.perPage)
              let maxBound = Math.min(this.currentPage * this.perPage, this.rows);
              if(this.rows === 0){
                  minBound = 0;
                  maxBound = 0;
              }
              console.log(minBound)
              return `Showing ${minBound} to ${maxBound} entries out of ${this.rows}`
          }
        },
        data() {
            return {
                fields: [
                    {key:"courseId", sortable:true, label:"Course ID"},
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
                search_filter: null,
                currentPage: 1,
                perPage: 10,
                rows: 0,
                pageOptions: [10, 25, 40, 100]

            }
        },
        created() {
            this.rows = this.course.length
        },
        methods:{
            onFiltered: function(filteredItems) {
                // Trigger pagination to update the number of buttons/pages due to filtering
                this.rows = filteredItems.length
                this.currentPage = 1
            }
        }
    };
</script>



