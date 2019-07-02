// All transactions related Get Requests are present here

module.exports = (app, db) => {
    // obtaining transaction from id 
    app.get( "/transaction/tID/:id", (req, res) =>
        db.author.findBytID(req.params.id).then( (result) => res.json(result))
    );

    // obtaining all transactions of app user (wholeseller) 
    app.get( "/transaction/wID/:wID", (req, res) =>
        db.author.findByWholesellerID(req.params.wID).then( (result) => res.json(result))
    );
  }