angular.module('mystops')
    .controller('myStopstController', ['$scope', 'location',
        function($scope, location) {
            var myStops = this;
            myStops.selectedLocation = '';
            myStops.show = [];

            myStops.changeTab = function(loc) {
                myStops.selectedLocation = loc;
                myStops.show = [];
                myStops.show.push(loc);
            };

        }]);