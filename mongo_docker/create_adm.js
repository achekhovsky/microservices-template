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
	{"second_id": UUID(), "version": "0.1", "second_name": "secondName_1", "description": "description_1", "some_data": "someData_1"},
	{"second_id": UUID(), "version": "0.1", "second_name": "secondName_2", "description": "description_2", "some_data": "someData_2"},
	{"second_id": UUID(), "version": "0.2", "second_name": "secondName_3", "description": "description_3", "some_data": "someData_3"},
	{"second_id": UUID(), "version": "0.3", "second_name": "secondName_4", "description": "description_4", "some_data": "someData_4"}
];
db.second_service.insertMany(doc);



