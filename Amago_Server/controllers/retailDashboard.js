const express = require('express');
const router = express.Router();
const moment = require('moment');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

//User Model Import
const db = require('../config/database');
const Users = db.import('../models/Users_updated');
const Harvest = db.import('../models/Inventory');

const itemKeys = require('../middleware/itemKeys');

router.get('/index', (req, res) => {
  res.render('index');
});

router.get('/:id', (req, res) => {
  Users.findOne({
    where: {
      userID: req.params.id
    }
  })
    .then(
      users =>
        (harvests = db
          .query(
            'SELECT "produce_name", "amount", "price" FROM (SELECT * FROM "Inventory" WHERE "status" IN (:status))A JOIN (SELECT "itemType","name" AS "produce_name" FROM "produceTable")B USING("itemType")',
            {
              raw: true,
              replacements: { status: '2' },
              type: db.QueryTypes.SELECT
            }
          )
          .then(harvest => {
            //console.log(itemName(harvest.itemType - 1));
            res.render('index', {
              data: {
                id: users.username,
                loggedIn: true,
                harv: harvest
              }
            });
          })
          .catch(err => console.log(err)))
    )
    .catch(err => console.log(err));
});
/*
  res.render('index', {
    data: {
      id: req.params.id,
      loggedIn: true,
      harv:[]
    }

  });
  */

function itemName(item) {
  key = [
    'Potato',
    'Onion',
    'Tomato',
    'Chilli',
    'Legume',
    'Cabbage',
    'Carrot',
    'Apple',
    'Banana',
    'Cauliflower',
    'Corn',
    'Grape',
    'Lemon',
    'Watermelon',
    'Mushroom',
    'Peanut',
    'Orange',
    'Papaya',
    'Peas',
    'Pineapple',
    'Pomegranate',
    'Gralic'
  ];
  return key[item];
}
//console.log(req.param.id);

//res.render('index');
//console.log(userID);

//});

module.exports = router;
