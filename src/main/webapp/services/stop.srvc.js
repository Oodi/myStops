angular.module('mystops')
    .factory('stopService', function ($rootScope, $http) {
        var APIURL = 'https://api.digitransit.fi/routing/v1/routers/hsl/index/graphql';



        function search(terms, callback) {
            $http.post(APIURL, JSON.stringify({ 'query' : '{stops(name:"' + terms + '") {gtfsId name url  code desc direction platformCode}}'})).then(callback);
        }

        function findStopStoptimes(id, callback) {
            $http.post(APIURL, JSON.stringify({ 'query' : '{stop(id:"' + id + '") {name platformCode stoptimesWithoutPatterns(numberOfDepartures:20) {realtimeDeparture realtimeArrival scheduledDeparture departureDelay ' +
            'trip {tripHeadsign tripShortName serviceId blockId shapeId route { type shortName longName}}}}}'})).then(callback);
        }


        var stop = {
            search: search,
            findStopStoptimes: findStopStoptimes
        };

        return stop;
    });