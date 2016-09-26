
angular.module('mystops')
    .controller('profileController', ['$scope', '$uibModalInstance' , 'userService',
        function($scope, $uibModalInstance, userService) {
            var profile = this;
            profile.alerts = [];

            profile.resetPsw = function() {

            };

            profile.checkUsername = function() {
                userService.validUsername(reg.username, function(response) {
                    if (response.data.status === 'OK') {
                        reg.alerts.push({
                            type: 'success',
                            dismiss: 4000,
                            msg: 'Käyttäjänimi ei ole varattu!'
                        });
                    } else {
                        reg.alerts.push({
                            type: 'danger',
                            msg: '' + response.data.error
                        });
                    }
                })
            };

            profile.closeAlert = function (index) {
                $scope.alerts.splice(index, 1);
            };

            profile.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };

        }]);