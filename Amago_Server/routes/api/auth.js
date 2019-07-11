const express = require('express');
const uuid = require('uuid');
const moment = require('moment');
const router = express.Router();

//User Model Import
const db = require('../../config/database');
const Users = db.import('../../models/Users_updated');

//const phone = req.body.phone;
//const password = req.body.password;

//GET single user
router.post('/login', (req, res) =>
  Users.findAll({
    where: {
      phone: req.body.phone
    }
  })
    .then(result => res.json(result))
    .catch(err => console.log(err))
);

module.exports = router;
