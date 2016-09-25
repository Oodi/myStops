
angular.module('mystops')
    .controller('loginController', ['$scope', 'auth',
        function($scope, auth) {
            var LOGINTEMPLATE = 'login/login.tpl.html';
            var self = this;
            self.credentials = {};

            self.login = function() {
                auth.authenticate(self.credentials, function(authenticated) {
                    if (authenticated) {

                        $scope.error = false;
                    } else {
                        $scope.error = true;
                    }
                })
            };

            var init  = function() {
                if (auth.getAuthStatus() !== false) {

                } else {
                    $scope.error = false;
                    $scope.authStatus = LOGINTEMPLATE;
                }
            };

            auth.authenticate([], init);
        }]);