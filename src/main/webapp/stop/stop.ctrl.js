angular.module('mystops')
    .controller('stopController', ['$scope', 'stopService',
        function($scope, stopService) {
            var stop = this;
            stop.stoptimes = [];


            stop.stopSchedule = function(id) {
                stopService.findStopStoptimes(id, function(response) {
                    find.selected = id;
                    find.selectedName = response.data.data.stop.name;
                    find.stoptimes = response.data.data.stop.stoptimesWithoutPatterns;
                });
            }

        }]);