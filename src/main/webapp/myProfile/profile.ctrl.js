
angular.module('mystops')
    .controller('profileController', ['$scope', '$uibModalInstance', '$window', 'base64' , 'userService', 'auth',
        function($scope, $uibModalInstance, $window, base64, userService, auth) {
            var profile = this;
            profile.alerts = [];
            profile.username = auth.getUsername();

            profile.resetPsw = function() {
                userService.resetPassword(base64.encode(profile.password), function(response) {
                    if (response.data.status === 'OK') {
                        profile.alerts.push({
                            type: 'success',
                            msg: 'Salasana vaihdettu'
                        });
                    } else {
                        profile.alerts.push({
                            type: 'danger',
                            msg: '' + response.data.error
                        });
                    }
                })
            };

            profile.deleteMe = function() {
                if (profile.sureDelete) {
                    userService.delUser( function(response) {
                        if (response.data.status === 'OK') {
                            profile.alerts.push({
                                type: 'success',
                                msg: 'Nyt lähtee'
                            });
                            auth.clear(function() {
                                $window.location.reload();
                            });

                        } else {
                            profile.alerts.push({
                                type: 'danger',
                                msg: '' + response.data.error
                            });
                        }
                    })
                } else {
                    profile.alerts.push({
                        type: 'danger',
                        msg: 'VIRHE'
                    });
                }

            };



            profile.closeAlert = function (index) {
                $scope.alerts.splice(index, 1);
            };

            profile.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };

        }]);