const express = require('express');
const uuid = require('uuid');
const moment = require('moment');
const router = express.Router();
const bcrypt = require('bcryptjs');

//User Model Import
const db = require('../../config/database');
const Users = db.import('../../models/Users_updated');

//GET all users
router.get(
  '/all',
  (req, res) => Users.findAll().then(result => res.json(result)) // ! Can't handle large data load, needs to be fixed
);

//GET single user
router.get('/:id', (req, res) =>
  Users.findAll({
    where: {
      userID: req.params.id
    }
  })
    .then(resultID => res.json(resultID))
    .catch(err => console.log(err))
);

//Register New Member || Create New User
// ! Need to add Validator
router.post('/register', (req, res) => {
  const newMember = {
    //userID: uuid.v4(),
    //userID: '7',
    username: req.body.username,
    password: req.body.password,
    phone: req.body.phone,
    createdAt: moment().format(),
    updatedAt: moment().format(),
    //email: req.body.email,
    userType: '1' // ! userType one set to 1 for farmers
  };
  // password hashing & saving new user
  bcrypt.genSalt(10, (err, salt) => {
    bcrypt.hash(newMember.password, salt, (err, hash) => {
      if (err) console.log(err);
      newMember.password = hash;
      console.log(newMember.password);
      let {
        //userID,
        username,
        password,
        phone,
        createdAt,
        updatedAt,
        userType
      } = newMember;
      // !Insert into User Table
      Users.create({
        //userID,
        username,
        password,
        phone,
        createdAt,
        updatedAt,
        userType
      })
        .then(res.status(200).json({ msg: 'User successfully registered' }))
        .catch(err => console.log(err));
    });
  });
  /*
  ! Need to check if the user already exists in DB 
  ! and handle accordingly 
*/
});
module.exports = router;
