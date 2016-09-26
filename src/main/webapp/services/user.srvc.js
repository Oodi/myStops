
angular.module('mystops')
    .factory('userService', ['$http', function ($http) {

        function validUsername(username, callback) {
            $http.post('/person/isUsernameTaken/', username).then(callback);
        }

        function createUser(user , callback) {
            $http.post('/person/newuser', user).then(callback);
        }

        function delUser(callback) {
            $http.delete('/person/delete/', {}).then(callback);
        }

        function resetPassword(newPassword, callback) {
            $http.post('/person/resetpassword/', newPassword).then(callback);
        }



        var userv = {
            validUsername: validUsername,
            createUser: createUser,
            delUser: delUser,
            resetPassword: resetPassword
        };

        return userv;
    }]);