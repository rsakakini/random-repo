
// Main app module
var app = angular.module('myApp', ['ui.bootstrap','angularUtils.directives.dirPagination']);

app.controller('airportsCtrl', function($scope, $http, $timeout, $uibModal, FileLoader) {


    $scope.airports = [];

    $scope.airportsWithCountryName = [];

    $scope.countries = [];

    $scope.typeOfRunways = [];

    $scope.mostCommonRunWays = [];

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

    function getMostCommonRunWays() {
        console.log("get data.............");
        FileLoader.getMostCommonRunWays().then(function(res){
            $scope.mostCommonRunWays = res.data;
            //console.log(res.data);
        }, function(err){
            console.log(err);
        });
    };


   // getAirportsData();

    getAirportsWithCountryName();

    getCountriesQuery();

    getTypeOfRunways();

   // getMostCommonRunWays();


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

    task.getMostCommonRunWays= function() {
        var defer = $q.defer();
        $http.get('/airport/query/mostCommonRunWays')
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
