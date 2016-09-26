angular.module('mystops')
    .controller('findController', ['$scope', 'stopService',
        function($scope, stopService) {
        var find = this;
            find.stops = [];
            find.stoptimes = [];
            find.selected = '';

            find.findStop = function() {
            stopService.search(find.findThis, function(response) {
                console.log(response.data.data.stops);
                find.stops = response.data.data.stops;
            });
        };

            find.stopSchedule = function(id) {
            stopService.findStopStoptimes(id, function(response) {
                console.log(response.data.data.stop);
                find.selected = id;
                find.stoptimes = response.data.data.stop.stoptimesWithoutPatterns;
            });
        }

        }]);

angular.module('mystops').filter('secondsToTime', function () {
    return function (time) {
        var h = parseInt( time / 3600 ) % 24;
        var m = parseInt( time / 60 ) % 60;
        if (m <10) {
            return h + ':0' + m;
        }
        return h + ':' + m;
    };
});