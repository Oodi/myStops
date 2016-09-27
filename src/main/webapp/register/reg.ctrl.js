
angular.module('mystops')
    .controller('registerController', ['$scope', '$uibModalInstance' , 'base64', 'userService',
        function($scope, $uibModalInstance, base64, userService) {
            var reg = this;
            reg.alerts = [];
            reg.newUser = {};
            reg.done = false;

            var alert = function (response, message) {
                reg.alerts = [];
                if (response.data.status === 'OK') {
                    reg.alerts.push({
                        type: 'success',
                        msg: message
                    });
                } else {
                    reg.alerts.push({
                        type: 'danger',
                        msg: '' + response.data.error
                    });
                }
            };

            reg.register = function() {
                userService.register(base64.encode(JSON.stringify(reg.newUser)),
                    function (response) {
                        if (response.data.status === 'OK') {
                            reg.newUser = {};
                            reg.password2 = '';
                            reg.done = true;
                        }
                        alert(response, 'Rekisteröinti onnistui, kirjaudu sisään!');
                    })
            };

            reg.checkUsername = function() {
              userService.validUsername(reg.newUser.username, function(response) {
                  alert(response, 'Käyttäjänimi on vapaa');
              })
            };

            reg.closeAlert = function (index) {
                reg.alerts.splice(index, 1);
            };

            reg.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };

        }]);