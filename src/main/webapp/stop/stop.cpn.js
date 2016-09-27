angular.module('mystops').component('stopnComponent', {
    templateUrl: 'stop/stop.tpl.html',
    controller: 'stopController',
    bindings: {
        id: '@'
    }
});