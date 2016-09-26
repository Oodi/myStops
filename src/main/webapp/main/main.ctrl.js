
angular.module('mystops')
    .controller('mainController', ['$scope' ,'$window', 'auth',
        function($scope,$window, auth) {

            $scope.logout = function() {
                auth.clear(function() {
                    $window.location.reload();
                });
            };

            var init  = function() {
                if (auth.getAuthStatus() !== false) {
                    $scope.loginStatus = true;
                } else {
                    $scope.error = false;
                }
            };

            auth.authenticate([], init);
        }]);