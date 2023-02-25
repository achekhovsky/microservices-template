db.createCollection("second_service");
db.createRole(
   {
     role: "secondServiceAdmRole",
     privileges: [
       { resource: { db: "secondServiceDb", collection: "" }, actions: [ "find", "update", "insert", "remove", "createCollection", "listCollections", "dropCollection", "listIndexes", "createIndex", "dropIndex", "dbStats" ] },
     ],
     roles: ["readWrite"]
   },
   { w: "majority" , wtimeout: 5000 }
);

db.createUser(
  {
    user: "Alex",
    pwd: "postgres", 
    roles: [ "secondServiceAdmRole" ]
  }
);
var doc = [
	{"_id": "b383ff39-f76f-4020-960c-74b0d5fb1ec3", "version": "0.1", "second_name": "secondName_1", "description": "description_1", "some_data": "someData_1"},
	{"_id": "5a7dde07-6e8a-4681-a0d6-0c3dc1cc005b", "version": "0.1", "second_name": "secondName_2", "description": "description_2", "some_data": "someData_2"},
	{"_id": "43fe9c0e-e05c-4fa6-87ba-4cdec514b4b1", "version": "0.2", "second_name": "secondName_3", "description": "description_3", "some_data": "someData_3"},
	{"_id": "86e0117c-7ff7-4e6f-ba10-95eb1b114eb5", "version": "0.3", "second_name": "secondName_4", "description": "description_4", "some_data": "someData_4"}
];
db.second_service.insertMany(doc);



