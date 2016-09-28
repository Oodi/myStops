angular.module('mystops')
    .controller('locationController', ['$scope', 'location',
        function($scope, locationService) {
            var location = this;
            var name = this.locationname;

            location.stops = [];

            location.deleteStop = function(stopid) {
                console.log(arrayObjectIndexOf(location.stops, stopid));
                location.stops = [];
            };


            var init = function() {
                location.stops = [];
                locationService.stopsOfLocation(name, function(response) {
                    location.stops = response.data;
                });

            };

            var arrayObjectIndexOf = function (myArray, searchTerm, property) {
                for (var i = 0, len = myArray.length; i < len; i++) {
                    if (myArray[i][property] === searchTerm) {
                        return i
                    }
                }
                return -1;
            };

            init();
        }]);