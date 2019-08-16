const express = require('express');
const router = express.Router();
const moment = require('moment');
const bcrypt = require('bcryptjs');

//User model import
const db = require('../config/database');
const Retail_users = db.import('../models/Users_updated');

router.get('/register', (req, res) => {
  res.render('register', { name: 'Register for a retail account' });
});

router.post('/register', (req, res) => {
  /*
    var register = {
    first: req.body.firstName,
    last: req.body.lastName,
    phone: req.body.phone,
    password: req.body.inputPassword
  };*/

  const newMember = {
    username: req.body.firstName + req.body.lastName,
    password: req.body.inputPassword,
    phone: req.body.phone,
    createdAt: moment().format(),
    updatedAt: moment().format(),
    //email: req.body.email,
    userType: '2' // ! userType one set to 2 for retailers
  };
  // password hashing & saving new user
  bcrypt.genSalt(10, (err, salt) => {
    bcrypt.hash(newMember.password, salt, (err, hash) => {
      if (err) console.log(err);
      newMember.password = hash;
      console.log(newMember.password);
      let {
        username,
        password,
        phone,
        createdAt,
        updatedAt,
        userType
      } = newMember;
      // !Insert into User Table
      Retail_users.create({
        //userID,
        username,
        password,
        phone,
        createdAt,
        updatedAt,
        userType
      })
        .then(res.render('login'))
        .catch(err => console.log(err));
    });
  });
  //console.log(register);
  //res.render('login');

  //res.render('home',{
  //     userValue : student,
  //     topicHead : 'Student Form'
});

module.exports = router;
