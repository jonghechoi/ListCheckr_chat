db.createUser({
    user: "chat",
    pwd: "1234",
    roles: [{
        role: "readWrite",
        db: "chat"
    }]
})