<template>
    <div id="app">
        <b-container fluid v-if="this.$cookies.isKey('username')">
            <b-row>
                <b-col></b-col>
                <b-col>
                    <div id="nav">
                        <router-link to="/"><BIconHouseFill></BIconHouseFill>Home</router-link>  |
                        <router-link to="/about">About</router-link>  |
                        <router-link to="/setting"><BIconGearFill></BIconGearFill>Setting</router-link>
                    </div>
                </b-col>
                <b-col><p style="padding:30px">Login as <b>{{this.$cookies.get("username")}}</b> | <b-button @click="logout" size="sm">Logout</b-button></p></b-col>
            </b-row>
        </b-container>
        <router-view/>
    </div>
</template>

<script>

    import axios from "axios";
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

</style>
