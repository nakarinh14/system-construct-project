<template>
    <div>
        <b-container>
            <b-form @submit="onSubmit">
                <b-row class="text-center" align-v="center">
                    <b-form-group
                            id="input-group-1"
                            label="ID:"
                            label-for="input-1"
                            class="text-center"
                    >
                        <b-form-input
                                id="input-1"
                                v-model="form.id"
                                type="id"
                                required
                                placeholder="uxxxxxxx or vxxxxxxx"
                        ></b-form-input>
                    </b-form-group>
                </b-row>
                <b-row  class="text-center" align-v="center">
                    <b-form-group id="input-group-2" label="Password:" label-for="input-2">
                        <b-form-input
                                id="input-2"
                                v-model="form.password"
                                required
                        ></b-form-input>
                    </b-form-group>

                </b-row>
                <b-row class="text-center" align-v="center">
                    <b-button type="submit" variant="primary">Submit</b-button>
                </b-row>
            </b-form>
        </b-container>
    </div>
</template>

<script>
    export default {
        name: 'Login',
        data() {
            return {
                form: {
                    id: '',
                    password: ''
                },
            }
        },
        methods: {
            onSubmit(evt) {
                evt.preventDefault()
                alert(JSON.stringify(this.form))
            }
        }
    }
</script>