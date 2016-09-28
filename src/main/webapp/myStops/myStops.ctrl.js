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
            myStops.locations = [];
            myStops.change = function () {
                init();

            };
            myStops.addLocation = function() {
                location.addNewLocation(myStops.newLocation, function() {
                    myStops.newLocation = '';
                    init();
                });
            };

            var init = function() {
                myStops.locations  = [];
                location.getLocations(function(response) {
                    angular.forEach(response.data, function (key) {
                        myStops.locations.push(key);
                    });
                    location.setLocations(myStops.locations);
                    if (myStops.locations.length > 0) {
                        myStops.changeTab(myStops.locations[0].name);
                    } else {
                        myStops.show = [];
                        myStops.selectedLocation = '';
                    }
                });

            };

            init();
        }]);