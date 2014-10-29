angular.module('collectApp').
    factory('recordService', ['$http', '$q', '$rootScope', 'eventHandlers', function ($http, $q, $rootScope, eventHandlers) {
        var record = {nodeById: {}};

        function handleEvent(event) {
            var handler = eventHandlers[event.type];
            handler(record, event);
        }

        function handleEvents(events) {
            angular.forEach(events, function (event) {
                handleEvent(event);
            });
        }

        function initSchema(schema) {
            schema.defById = {};
            registerDefinitions(schema, schema.defById);
            return schema;
        }

        function registerDefinitions(def, defById) {
            defById[def.id] = def;
            if (def.member)
                registerDefinitions(def.member, defById);
            if (def.members)
                angular.forEach(def.members, function (member) {
                    registerDefinitions(member, defById);
                });
        }

        function loadSchemaAndRecord(surveyId, recordId) {
            var deferred = $q.defer();

            $q.all([loadSchema(surveyId), listenToRecord(surveyId, recordId)]).
                then(function (result) {
                    record.surveyId = surveyId;
                    record.recordId = recordId;
                    record.schema = initSchema(result[0].data);
                    var events = result[1];
                    handleEvents(events);
                    deferred.resolve(record);
                }).
                catch(function (data, status, headers, config) {
                    deferred.reject("Failed"); // TODO: Need to provide some details on the error here
                });

            return deferred.promise;
        }

        function listenToRecord(surveyId, recordId) {
            var deferred = $q.defer();
            var source = new EventSource('json/record?surveyId=' + surveyId + '&recordId=' + recordId);
            source.addEventListener('recordEvent', function (msg) {
                $rootScope.$apply(function() {
                    var events = angular.fromJson(msg.data);
                    if (record.schema)
                        handleEvents(events);
                    deferred.resolve(events);
                }, false);

                });
            return deferred.promise;
        }

        function loadSchema(surveyId) {
            return $http.get('json/schema', {
                params: {
                    surveyId: surveyId
                }
            });
        }

        function updateAttribute(attribute) {
            $http.post('json/update-attribute', {
                surveyId: record.surveyId,
                recordId: record.recordId,
                attributeId: attribute.id,
                value: attribute.value
            })
        }

        return {
            'loadRecord': loadSchemaAndRecord,
            'updateAttribute': updateAttribute
        };
    }]);