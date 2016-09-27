angular.module('mystops').component('locationComponent', {
    templateUrl: 'location/location.tpl.html',
    controller: 'locationController',
    bindings: {
        location: '@'
    }
});