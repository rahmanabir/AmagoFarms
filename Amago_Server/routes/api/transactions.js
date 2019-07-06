const express = require('express');
const router = express.Router();
const db = require('../../config/database');
const Sequelize = require('sequelize');
const Op = Sequelize.Op;
// const dataTypes = ;

// const transM = require('../../models/Transactions');
// const trans  = transM(db,dataTypes)

const trans = db.import('../../models/Transactions');


// obtaining transaction from id 
router.get( "/", (req, res) =>
    res.status(400).json({msg: `you seem lost, please read shin-api documentation. wiki: tID/:tID or wID/:wID to filter`})
);

// obtaining transaction from id 
router.get( "/tID/:id", (req, res) =>
    trans.findAll(
        {
            where: {
                tID: req.params.id
            }
        }

    ).then( (result) => res.json(result))
);

// obtaining all transactions of app user (wholeseller) 
router.get( "/wID/:wID", (req, res) =>
    trans.findAll(
        {
            where: {
                WholesellerID: req.params.wID
            }
        }   
    ).then( (result) => res.json(result))
);

module.exports = router;