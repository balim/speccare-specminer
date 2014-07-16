define(['jquery', 'app/foo/printer'], function($, printerConstructor) {
    describe("Printer should", function () {
        "use strict";

        var hook, printer;
        beforeEach(function() {
            hook = hook = $('<div></div>');
            spyOn(hook, 'html');

            printer = printerConstructor(hook);
        });

        it("display its own info", function () {
            printer.info();
            expect(hook.html.mostRecentCall.args[0]).toBe('RequireJs also works!');
        });

        it("display additional info if passed", function () {
            printer.info('additional info');
            expect(hook.html.mostRecentCall.args[0]).toBe('RequireJs also works! - additional info');
        });
    });
});
