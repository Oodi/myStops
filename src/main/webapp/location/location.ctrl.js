angular.module('mystops')
    .controller('locationController', ['$scope', '$uibModal', 'location',
        function ($scope, $uibModal, locationService) {
            var location = this;
            location.name = this.locationname;
            location.stops = [];

            location.deleteStop = function (stopid) {
                var data = {};
                data.stopID = stopid;
                data.location = location.name;
                locationService.deleteStopFromLocation(data, function () {
                    init();
                });
            };

            $scope.$on('addLocation', function(event, obj) {
                if(location.name === obj.selectedList) {
                   init();
                }
            });

            location.edit = function () {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'location/locationEdit.tpl.html',
                    controller: 'locationEditController',
                    controllerAs: 'lEdit',
                    resolve: {
                        getLocationName: function () {
                            return location.name;
                        }
                    }
                });
                modalInstance.result.then(function (result) {
                    location.onChange();
                });
            };

            var init = function () {
                location.stops = [];
                locationService.stopsOfLocation(location.name, function (response) {
                    location.stops = response.data;
                });

            };

            init();
        }]);