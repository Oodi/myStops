
angular.module('mystops')
    .factory('location', ['$http', function ($http) {

        var locations = [];

        function getLocationsArray() {
            return locations;
        }
        function setLocations(location) {
            locations = location;
        }

        function getLocations(callback) {
            $http.get('/mystop/location', {}).then(callback);
        }

        function addNewLocation(name , callback) {
            $http.post('/mystop/location', name).then(callback);
        }

        function deleteLocation(name, callback) {
            $http({
                method: 'DELETE',
                url: '/mystop/location',
                data: name
            }).then(callback);
        }

        function changeLocationName(names, callback) {
            $http.post('/mystop/locationName', names).then(callback);
        }

        function addStopToLocation(data, callback) {
            $http.post('/mystop/stop', data).then(callback);
        }

        function stopsOfLocation(location, callback) {
            $http.post('/mystop/stopOfLocation', location).then(callback);

        }

        function deleteStopFromLocation(data, callback) {
            $http({
                    method: 'DELETE',
                    url: '/mystop/stop',
                    data: data,
                headers: {'Content-Type': 'application/json;charset=utf-8'}
        }).then(callback);
        }



        var location = {
            getLocationsArray: getLocationsArray,
            setLocations: setLocations,
            getLocations: getLocations,
            addNewLocation: addNewLocation,
            deleteLocation: deleteLocation,
            changeLocationName: changeLocationName,
            addStopToLocation: addStopToLocation,
            stopsOfLocation: stopsOfLocation,
            deleteStopFromLocation: deleteStopFromLocation
        };

        return location;
    }]);