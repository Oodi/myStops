angular.module('mystops')
    .controller('findController', ['$scope', 'stopService',
        function($scope, stopService) {
            $scope.stops = [];
            $scope.stoptimes = [];


        $scope.findStop = function() {
            stopService.search($scope.findThis, function(response) {
                console.log(response.data.data.stops);
                $scope.stops = response.data.data.stops;
            });
        };

        $scope.stopSchedule = function(id) {
            stopService.findStopStoptimes(id, function(response) {
                console.log(response.data.data.stop);
                $scope.stoptimes = response.data.data.stop.stoptimesWithoutPatterns;
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