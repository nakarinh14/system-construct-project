<template>
    <b-container>
        <b-form>
            <b-form-group
                    id="input-group-1"
                    label="Term period:"
                    label-for="input-id"
            >
                <b-form-input
                        id="input-id"
                        v-model="form.term"
                        type="text"
                        required
                        placeholder="Enter term period (Summer, Term I, Term II, etc.)"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-2"
                    label="Year period:"
                    label-for="input-id"
            >
                <b-form-input
                        id="input-id"
                        v-model="form.year"
                        type="text"
                        required
                        placeholder="Enter year period (2020-2021, 2019-2020, etc.)"
                ></b-form-input>
            </b-form-group>
        </b-form>
    </b-container>
</template>

<script>
    export default {
        name: "AddTermForm",
        data(){
            return {
                form:{
                    term: "",
                    year: ""
                }
            }
        },

    }
</script>

<style scoped>

</style>