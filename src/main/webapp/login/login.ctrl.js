
angular.module('mystops')
    .controller('loginController', ['$scope', '$uibModalInstance', '$window' , 'auth',
        function($scope, $uibModalInstance, $window, auth) {
            var login = this;
            login.credentials = {};
            login.loginStatus = false;
                
            login.login = function() {
                auth.authenticate(login.credentials, function(authenticated) {
                    if (authenticated) {
                        login.loginStatus = true;
                        $scope.error = false;
                        $uibModalInstance.close(true);
                        $window.location.reload();
                    } else {
                        $scope.error = true;
                    }
                })
            };

            login.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };

            var init  = function() {
                if (auth.getAuthStatus() !== false) {
                    login.loginStatus = true;
                } else {
                    $scope.error = false;
                }
            };

            auth.authenticate([], init);
        }]);