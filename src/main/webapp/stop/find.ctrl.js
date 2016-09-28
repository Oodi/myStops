angular.module('mystops')
    .controller('findController', ['$scope', 'stopService', 'location',
        function($scope, stopService, location) {
        var find = this;
            find.myInterval = 5000;
            find.image = 'assets/img/ohje1.jpg';
            find.image2 = 'assets/img/Ohje2.jpg';
            find.slides = [];
            find.slides.push({
                image: find.image
            });
            find.slides.push({
                image: find.image2
            });

            find.stops = [];
            find.stoptimes = [];
            find.selected = '';

            find.findStop = function() {
            stopService.search(find.findThis, function(response) {
                find.search = true;
                find.stops = response.data.data.stops;
            });
        };

            find.findStopNow = function() {
                if (find.findThis.length > 2) {
                    find.findStop();
                }
            };

            find.stopSchedule = function(id) {
            stopService.findStopStoptimes(id, function(response) {
                find.locations = location.getLocationsArray();
                find.selected = id;
                find.selectedName = response.data.data.stop.name;
                find.stoptimes = response.data.data.stop.stoptimesWithoutPatterns;
            });
        };

        find.addStopToLocation = function () {
            var data = {};
            data.stopID = find.selected;
            data.location = find.SelectedList;
            location.addStopToLocation(data, function() {
                $scope.$broadcast('addLocation' , {selectedList: find.SelectedList});
            });
        };

        find.cancel = function() {
            find.search = false;
            find.selected = '';
            find.stops = [];
            find.findThis = '';
        };

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