
print("Started Adding the Users.");
db = db.getSiblingDB("admin");
db.createUser({
   user: "dhimahi_user",
   pwd: "dhimahi_password",
   roles: [{ role: "readWrite", db: "meteodb" }],
});
print("End Adding the User Roles.");

db = db.getSiblingDB('meteodb');
db.meteos.createIndex({location: "2dsphere"});

