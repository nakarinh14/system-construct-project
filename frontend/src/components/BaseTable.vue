<template>
    <div>
        <!-- Search bar and per page options-->
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
                                v-model="searchFilter"
                                placeholder="Search"
                                type="search"
                                class="search-bar"
                        >
                        </b-form-input>
                    </b-input-group>
                </b-col>
            </b-row>
        </b-container>
        <!-- Actual table component  -->
        <component
                :is="tableComponent"
                :data="data"
                :busy="busy"
                :searchFilter="searchFilter"
                :currentPage="currentPage"
                :perPage="perPage"
                v-on:filterUpdated="updatePagination"
                v-on:fetchData="fetchData"
        ></component>
        <!-- Footer that show many result is shown this page -->
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

    import StudentDashboard from '@/components/StudentDashboard.vue'
    import InstructorDashboard from '@/components/InstructorDashboard.vue';
    import AdminCourseTable from '@/components/AdminCourseTable.vue';
    import AdminUserTable from '@/components/AdminUserTable.vue';

    export default {
        name: "BaseTable",
        props: ['data', 'tableComponent'],
        components: {
            StudentDashboard,
            InstructorDashboard,
            AdminCourseTable,
            AdminUserTable
        },
        data(){
            return{
                searchFilter: null,
                busy: false,
                currentPage: 1,
                perPage: 10,
                rows: 0,
                pageOptions: [10, 25, 40, 100]
            }
        },
        watch:{
            data: function(){
                this.updatePagination(this.data.length)
            }
        },
        methods: {
            updatePagination: function(filteredLength){
                this.rows = filteredLength
                this.currentPage = 1
            },
            fetchData: function(){
                this.$emit('fetchData')
            }
        },
        computed: {
            currentPageCount: function(){
                let minBound = 1 + ((this.currentPage-1)*this.perPage)
                let maxBound = Math.min(this.currentPage * this.perPage, this.rows);
                if(this.rows === 0){
                    minBound = 0;
                    maxBound = 0;
                }
                return `Showing ${minBound} to ${maxBound} entries out of ${this.rows}`
            }
        },
    }
</script>

<style scoped>

</style>