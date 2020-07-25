<template>
    <div id="app">

        <b-container fluid v-if="this.$cookies.isKey('username')">
            <b-row align-v="center">
                <b-col></b-col>
                <b-col>
                    <div id="nav">
                        <router-link to="/">
                            <span v-if="this.$cookies.get('role') !== 'admin'"><BIconHouseFill class="icon-pad"></BIconHouseFill>Home</span>
                            <span v-else><BIconGearFill class="icon-pad"></BIconGearFill>Admin </span>
                        </router-link>  |
                        <router-link to="/about"><BIconInfoCircleFill class="icon-pad"></BIconInfoCircleFill>About</router-link>  |
                    </div>
                </b-col>
                <b-col>
                    <div >
                        <b-avatar badge="S" badge-variant="info" v-if="this.$cookies.get('role') === 'student'" size="33px"></b-avatar>
                        <b-avatar badge="I" badge-variant="primary" v-if="this.$cookies.get('role') === 'instructor'" size="33px"></b-avatar>
                        <b-avatar badge-variant="warning" v-if="this.$cookies.get('role') === 'admin'" size="33px">
                            <template v-slot:badge><b-icon icon="star-fill" variant="white"></b-icon> </template>
                        </b-avatar>
                        <span style="padding:10px; font-size: 15px">Logged in as
                            <strong>{{this.$cookies.get("username")}}</strong> |
                            <a href="#" @click.prevent="logout">Logout</a>
                        </span>
                    </div>
                </b-col>
            </b-row>
        </b-container>
        <router-view/>
    </div>
</template>

<script>

    import axios from "axios";
    import 'bootstrap/dist/css/bootstrap.css';
    import 'bootstrap-vue/dist/bootstrap-vue.css';

    export default {
        name: 'app',
        methods: {
            logout: function() {
                const postUrl = "http://localhost:8081/api/auth/logout";
                axios.post(postUrl, {}, {withCredentials: true})
                    .then(response =>{
                        if(response.data.status === "success") {
                            console.log("nice")
                            this.$cookies.remove("username");
                            this.$cookies.remove("firstname");
                            this.$cookies.remove("lastname");
                            this.$cookies.remove("role");
                            this.$router.push("/login");
                        }
                    })
                    .catch(() => {
                        console.log("logout REST called failed");
                    })
            }
        }
    }

</script>

<style>
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }

    #nav {
        padding: 30px;
    }

    #nav a {
        font-weight: bold;
        color: #2c3e50;
        margin-right: 5px;
        margin-left: 5px;
    }

    #nav a.router-link-exact-active {
        color: #42b983;
    }
    .icon-pad {
        margin-left: 5px;
        margin-right: 5px;
    }

</style>
