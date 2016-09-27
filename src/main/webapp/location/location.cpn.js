angular.module('mystops').component('locationComponent', {
    templateUrl: 'location/location.tpl.html',
    controller: 'locationController',
    controllerAs: 'location',
    bindings: {
        locationname: '@'
    }
});