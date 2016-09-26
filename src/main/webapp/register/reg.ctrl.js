
angular.module('mystops')
    .controller('registerController', ['$scope', '$uibModalInstance' , 'userService',
        function($scope, $uibModalInstance, userService) {
            var reg = this;
            reg.alerts = [];

            reg.register = function() {

            };

            reg.checkUsername = function() {
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

            reg.closeAlert = function (index) {
                reg.alerts.splice(index, 1);
            };

            reg.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };

        }]);