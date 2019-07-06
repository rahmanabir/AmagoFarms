const express = require('express');
const uuid = require('uuid');
const router = express.Router();
//const Sequelize = require('sequelize');

const db = require('../../config/database');
//const Users = require('../../models/Users');
const Users = db.import('../../models/Users');
//const users = require('../../Users'); // TODO Change to user model

//  ! GET all users
router.get('/all', (req, res) => {
  res.json(Users);
});

// ! GET single user
router.get('/:id', (req, res) => {
  const found = Users.some(
    member => member.userID === parseInt(req.params.userID)
  );

  if (found) {
    res.json(
      Users.filter(member => member.userID === parseInt(req.params.userID))
    );
  } else {
    res
      .status(400)
      .json({ msg: `No member with the id of ${req.params.userID}` });
  }
});

// ! Register New Member || Create New User
router.post('/register', (req, res) => {
  const newMember = {
    //userID: uuid.v4(),
    userID: '5',
    username: req.body.username,
    password: req.body.password,
    createdAt: 'Fri Mar 22 2013',
    updatedAt: 'Fri Mar 23 2013',
    //email: req.body.email,
    userType: '1'
  };

  if (!newMember.username) {
    return res
      .status(400)
      .json({ msg: 'Please include a username and userType' });
  } else {
    let {
      userID,
      username,
      password,
      createdAt,
      updatedAt,
      userType
    } = newMember;
    // !Insert into User Table
    Users.create({
      userID,
      username,
      password,
      createdAt,
      updatedAt,
      userType
    })
      .then(user => console.log('User inserted into table'))
      .catch(err => console.log(err));
    //db.push(newMember);
    //res.json(Users)
  }
});
module.exports = router;
