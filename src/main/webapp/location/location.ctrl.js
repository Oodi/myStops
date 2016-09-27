angular.module('mystops')
    .controller('locationController', ['$scope', 'location',
        function($scope, locationService) {
            var location = this;
            var name = this.locationname;

            location.stops = [];


            var init = function() {
                location.stops = [];
                locationService.stopsOfLocation(name, function(response) {
                    console.log(response);
                    location.stops = response.data;
                    console.log(location.stops);
                });

            };

            init();
        }]);