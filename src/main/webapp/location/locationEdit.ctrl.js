
angular.module('mystops')
    .controller('locationEditController', ['$scope', '$uibModalInstance', 'getLocationName', 'location',
        function($scope, $uibModalInstance, getLocationName, location) {
            var lEdit = this;
            lEdit.alerts = [];

            lEdit.changeName = function() {
                    location.changeLocationName({'oldName': getLocationName, 'newName': lEdit.name}, function(response) {
                        if (response.data.status === 'OK') {
                            lEdit.alerts.push({
                                type: 'success',
                                msg: 'Nimenvaihto onnistui'
                            });
                            $uibModalInstance.close(true);
                        } else {
                            lEdit.alerts.push({
                                type: 'danger',
                                msg: '' + response.data.error
                            });
                        }
                    })
            };

            lEdit.deleteMe = function () {
                if (lEdit.sureDelete) {
                    location.deleteLocation(getLocationName, function(response) {
                        if (response.data.status === 'OK') {
                            lEdit.alerts.push({
                                type: 'success',
                                msg: 'Lista poistuu'
                            });
                            $uibModalInstance.close(true);
                        } else {
                            lEdit.alerts.push({
                                type: 'danger',
                                msg: '' + response.data.error
                            });
                        }
                    });
                } else {
                    lEdit.alerts.push({
                        type: 'danger',
                        msg: 'VIRHE'
                    });
                }

            };

            lEdit.closeAlert = function (index) {
                $scope.alerts.splice(index, 1);
            };

            lEdit.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };

        }]);