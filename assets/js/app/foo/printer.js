define([], function () {

    var that = function(hook) {
        return {
            'info': function(additionalInfo) {
                additionalInfo = (additionalInfo === undefined) ? '' : ' - ' + additionalInfo;
                hook.html("RequireJs also works!" + additionalInfo);
            }
        };
    };

    return that;
});
