angular.module('mystops')
    .controller('locationListController', ['$scope', 'location',
        function($scope, location) {
            var locationList = this;
            locationList.locations = [];

            locationList.addLocation = function() {
                location.addNewLocation(locationList.newLocation, function() {
                    locationList.newLocation = '';
                    init();
                });
            };




            var init = function() {
                locationList.locations  = [];
                location.getLocations(function(response) {
                    angular.forEach(response.data, function (key) {
                        locationList.locations.push(key);
                    });
                    location.setLocations(locationList.locations);
                });
            };

            init();
        }]);