
angular.module('mystops')
    .controller('mainController', ['$scope' ,'$window', 'auth',
        function($scope,$window, auth) {
            var MAINPAGE = 'main/mainpage.tpl.html';
            $scope.logout = function() {
                auth.clear(function() {
                    $window.location.reload();
                });
            };

            var init  = function() {
                if (auth.getAuthStatus() !== false) {
                    $scope.loginStatus = true;
                    $scope.mainpage = MAINPAGE;
                } else {
                    $scope.error = false;
                }
            };

            auth.authenticate([], init);
        }]);