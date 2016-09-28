angular.module('mystops')
    .controller('stopController', ['$scope', 'stopService',
        function($scope, stopService) {
            var stop = this;
            stop.stoptimes = [];
            var id = this.stopid;

            stop.delete = function() {
                stop.onDelete({stopid: id});
            };

            stop.stopSchedule = function() {
                stopService.findStopStoptimes(id, function(response) {
                    stop.selected = id;
                    stop.selectedName = response.data.data.stop.name;
                    stop.stoptimes = response.data.data.stop.stoptimesWithoutPatterns;
                });
            };
            stop.stopSchedule();
        }]);