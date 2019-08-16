const express = require('express');
const router = express.Router();
const moment = require('moment');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

//User model import
const db = require('../config/database');
const Retail_users = db.import('../models/Users_updated');

//Secret Key access for JWT signature validation
const keys = require('../config/keys');

//JWT token extractor middleware
const verifyToken = require('../middleware/jwtMiddleware');

module.exports = router;

router.get('/login', (req, res) => {
  res.render('login');
});

router.post('/login', (req, res) =>
  Retail_users.findAll({
    where: {
      phone: req.body.phone
    },
    raw: true //parsing seqeulize model
  })
    .then(member => {
      //Check for user
      if (!member) {
        return res.render('login', { msg: 'Not a registered user!' });
      }

      const password = req.body.inputPassword; //password
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
          // !DEBUG

          res.render('index'); // FIX JWT TOKEN HANDLING

          // !DEBUG
          //Sign Token
          jwt.sign(
            payload,
            keys.secretOrKey,
            { expiresIn: 3060 },
            (err, token) => {
              res.json({
                token: 'Bearer ' + token
              });
              res.render('index');
            }
          );
        } else {
          // Passwords don't match
          return res.render('login', { msg: 'Wrong password!, Try again!' });
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
