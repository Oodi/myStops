angular.module('mystops')
    .controller('myStopstController', ['$scope', 'location',
        function($scope, location) {
            var myStops = this;
            myStops.selectedLocation = '';

            myStops.changeTab = function(loc) {
                myStops.selectedLocation = loc;
            };

        }]);