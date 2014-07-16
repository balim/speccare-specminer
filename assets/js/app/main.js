define(['jquery', 'domReady!', 'app/foo/printer'], function ($, doc, printer) {
    var p = printer($('#printer'));
    p.info("called from app/main");
    $('#notbuilt').hide();
    $('#built').show();
});
