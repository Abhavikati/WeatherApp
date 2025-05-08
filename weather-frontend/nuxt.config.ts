// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: true,
  runtimeConfig: {
    public: {
      apiBase: 'http://localhost:8080/api'
    }
  }
})

