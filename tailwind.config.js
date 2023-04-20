/** @type {import('tailwindcss').Config} */
module.exports = {

    purge: {
        content: ["./src/main/resources/templates/**/*.{html,js}"],
        safelist: ['object-contain', 'h-24', 'text-base', 'm-0', 'animate-spin']
    },
    theme: {
        extend: {},
    },
    plugins: [],
}
