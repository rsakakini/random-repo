
// Main app module
var app = angular.module('myApp', ['ui.bootstrap', 'confirmDialogBoxModule','angularUtils.directives.dirPagination']);

app.controller('airportsCtrl', function($scope, $http, $timeout, $uibModal, FileLoader) {

//$scope.$watch()

   $scope.airports = [];

   $scope.airportsWithCountryName = [];

   $scope.countries = [];

   $scope.typeOfRunways = [];

   function getAirportsData() {
          console.log("get data.............");
                  FileLoader.getAllAir().then(function(res){
                        $scope.airports = res.data;
                        //console.log(res.data);
                   }, function(err){
                      console.log(err);
                   });
          };

   function getAirportsWithCountryName() {
          console.log("get data.............");
             FileLoader.getAirportsWithCountryName().then(function(res){
                    $scope.airportsWithCountryName = res.data;
                        //console.log(res.data);
                   }, function(err){
                      console.log(err);
                   });
          };


   function getTypeOfRunways() {
            FileLoader.getTypeOfRunways().then(function(res){
                  $scope.typeOfRunways = res.data;
                  //console.log(res.data);
             }, function(err){
                console.log(err);
             });
    };

   function getCountriesQuery() {
        console.log("get data.............");
            FileLoader.getCountriesQuery().then(function(res){
                    $scope.countries = res.data;
                    //console.log(res.data);
            }, function(err){
             console.log(err);
              });
    };


   // getAirportsData();

    getAirportsWithCountryName();

    getCountriesQuery();

    getTypeOfRunways();


  });


/**
 * Directive for alert notification. You can also use angular ui-bootstrap for better alert notifications
 */
app.directive('notification', function($timeout){
  return {
    restrict: 'E',
    replace: true,
    scope: {
      ngModel: '='
    },
    template: '<div ng-class="ngModel.type" class="alert alert-box">{{ngModel.content}}</div>',
     link: function(scope, element, attrs) {
          $timeout(function(){
            element.hide();
          }, 3000);
      }
  }
});




app.service("FileLoader", function($http, $q) {

   var task = this;
   task.taskList = {};

      task.getAllAir = function() {
                var defer = $q.defer();
                $http.get('/airport/list')
                .success(function(res){
               // console.log("task" + res);
                      task.taskList = res;
                      defer.resolve(res);
                 })
                 .error(function(err, status){
                    defer.reject(err);
                 });

               return defer.promise;
           }

            task.getAirportsWithCountryName = function() {
                var defer = $q.defer();
                $http.get('/airport/airportWithCountryName')
                .success(function(res){
               // console.log("task" + res);
                      task.taskList = res;
                      defer.resolve(res);
                 })
                 .error(function(err, status){
                    defer.reject(err);
                 });

               return defer.promise;
           }



      task.getCountriesQuery= function() {
           var defer = $q.defer();
            $http.get('/airport/query/countries')
            .success(function(res){
        // console.log("task" + res);
           task.taskList = res;
            defer.resolve(res);
          })
            .error(function(err, status){
            defer.reject(err);
           });

      return defer.promise;
              }


        task.getTypeOfRunways= function() {
           var defer = $q.defer();
            $http.get('/airport/query/typeRunways')
            .success(function(res){
        // console.log("task" + res);
           task.taskList = res;
            defer.resolve(res);
          })
            .error(function(err, status){
            defer.reject(err);
           });

      return defer.promise;
              }



   return task;

 });


/**

 * Module for confirm dialog box
 * To use this, add this module as a dependency in app module.
 */
angular.module('confirmDialogBoxModule', ['ui.bootstrap'])
  .directive('ngConfirmClick', ['$uibModal', function($uibModal) {

      var modalInstanceCtrl = function($scope, $uibModalInstance) {
        $scope.ok = function() {
          $uibModalInstance.close();
        };

        $scope.cancel = function() {
          $uibModalInstance.dismiss('cancel');
        };
      };

      return {
        restrict: 'A',
        scope:{
          ngConfirmClick:"&"
        },
        link: function(scope, element, attrs) {
          element.bind('click', function() {
            var message = attrs.ngConfirmMessage || "Are you sure ?";

            // Template for confirmation dialog box
            var modalHtml = '<div class="modal-body">' + message + '</div>';
            modalHtml += '<div class="modal-footer"><button class="btn btn-primary" ng-click="ok()">OK</button><button class="btn btn-default" ng-click="cancel()">Cancel</button></div>';

            var modalInstance = $uibModal.open({
              template: modalHtml,
              controller: modalInstanceCtrl
            });

            modalInstance.result.then(function() {
              scope.ngConfirmClick();
            }, function() {
              //Modal dismissed
            });
          });

        }
      }
    }
  ]);
