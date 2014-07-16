requirejs.config({
    paths: {
        jquery: 'vendor/jquery/dist/jquery.min',
        bootstrap: 'vendor/bootstrap/dist/js/bootstrap.min',
        domReady: 'vendor/requirejs-domready/domReady'
    },
    shim: {
        bootstrap: ['jquery']
    }
});
