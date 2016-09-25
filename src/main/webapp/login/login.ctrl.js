
angular.module('mystops')
    .controller('loginController', ['$scope', 'auth',
        function($scope, auth) {
            var LOGINTEMPLATE = 'login/login.tpl.html';
            var self = this;
            self.credentials = {};

            self.login = function() {
                auth.authenticate(self.credentials, function(authenticated) {
                    if (authenticated) {
                        console.log("jeos");
                        $scope.error = false;
                    } else {
                        $scope.error = true;
                    }
                })
            };

            var init  = function() {
                if (auth.getAuthStatus() !== false) {
                    $scope.authStatus = 'findStop/find.tpl.html';
                } else {
                    $scope.error = false;
                    $scope.authStatus = LOGINTEMPLATE;
                }
            };

            auth.authenticate([], init);
        }]);