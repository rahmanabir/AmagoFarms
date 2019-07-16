const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

//User Model Import
const db = require('../../config/database');
const Users = db.import('../../models/Users_updated');

//Secret Key access for JWT signature validation
const keys = require('../../config/keys');

//JWT token extractor middleware
const verifyToken = require('../../middleware/jwtMiddleware');

//GET single user
router.post('/loginTest', (req, res) =>
  Users.findAll({
    where: {
      phone: req.body.phone
    }
  })
    .then(result => res.json(result))
    .catch(err => console.log(err))
);

// @route POST api/auth/login
// @description Login User | Returns JWT token
// @access Public
router.post('/login', (req, res) =>
  Users.findAll({
    where: {
      phone: req.body.phone
    },
    raw: true //parsing seqeulize model
  })
    .then(member => {
      //Check for user
      if (!member) {
        return res.status(404).json({ msg: ' not a registered user' });
      }

      const password = req.body.password; //password
      const memberData = member[0]; // member[0] is an array returned after query
      // Check Password

      bcrypt.compare(password, memberData.password, (err, isMatch) => {
        console.log(err + isMatch + password + memberData.password);
        if (isMatch) {
          // Passwords match
          //Create JWT payload
          console.log(memberData.password);
          const payload = {
            id: memberData.userID,
            username: memberData.username
          };

          //Sign Token
          jwt.sign(
            payload,
            keys.secretOrKey,
            { expiresIn: 3060 },
            (err, token) => {
              res.json({
                token: 'Bearer ' + token
              });
            }
          );
        } else {
          // Passwords don't match
          return res.status(400).json({ msg: 'Incorrect Password' });
        }
      });
    })
    .catch(err => console.log(err))
);

router.post('/current', verifyToken, (req, res) => {
  jwt.verify(req.token, keys.secretOrKey, (err, authData) => {
    if (err) {
      res.sendStatus(403);
    } else {
      res.json({
        authData
      });
    }
  });
});

module.exports = router;
