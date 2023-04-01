/** @type {import('tailwindcss').Config} */
module.exports = {

    purge: {
        content: ["./src/main/resources/templates/**/*.{html,js}"],
        safelist: ['object-contain', 'h-24']
    },
    theme: {
        extend: {},
    },
    plugins: [],
}
