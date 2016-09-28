angular.module('mystops').component('stopComponent', {
    templateUrl: 'stop/stop.tpl.html',
    controller: 'stopController',
    controllerAs: 'stop',
    bindings: {
        stopid: '@',
        onDelete: '&'
    }
});