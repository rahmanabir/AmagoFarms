const express = require('express');
const router = express.Router();
const moment = require('moment');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

router.get('/index', (req, res) => {
  res.render('index');
});

module.exports = router;
