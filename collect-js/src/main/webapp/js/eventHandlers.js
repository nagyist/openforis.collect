angular.module('collectApp').
    factory('eventHandlers', function () {
        return {

            EntityAdded: function (record, event) {
                var entity = addNode(record, event, 'Entity');
                entity.members = [];
            },

            EntityListAdded: function (record, event) {
                var entityList = addNode(record, event, 'EntityList');
                entityList.members = [];
            },

            AttributeAdded: function (record, event) {
                var attribute = addNode(record, event, 'Attribute');
                attribute.value = event.value;
            },

            AttributeValueUpdated: function (record, event) {
                var attribute = record.nodeById[event.id];
                attribute.value = event.value;
            }
        };

        function addNode(record, event, type) {
            var def = definition(record, event);
            var node = {
                type: type,
                id: event.id,
                label: def.label
            };
            record.nodeById[node.id] = node;

            if (event.parentId)
                record.nodeById[event.parentId].members.push(node);
            else
                record.rootEntity = node;

            return node;
        }

        function definition(record, event) {
            return record.schema.defById[event.defId];
        }
    });
