angular.module('mystops')
    .controller('loginNavController', ['$scope', '$uibModal',
        function($scope, $uibModal) {
            var login = this;
            login.loginStatus = false;

            login.startLogin = function() {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'login/login.tpl.html',
                    controller: 'loginController',
                    controllerAs: 'login'
                });
            };

            login.register = function() {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'register/reg.tpl.html',
                    controller: 'registerController',
                    controllerAs: 'reg'
                });
            };

            login.profile = function() {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'myProfile/profile.tpl.html',
                    controller: 'profileController',
                    controllerAs: 'profile'
                });
            };


        }]);